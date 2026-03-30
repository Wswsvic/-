package com.atguigu.lease.web.app.controller.myroom;

import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.model.entity.RepairRecord;
import com.atguigu.lease.model.entity.RoomInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.app.service.*;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.atguigu.lease.web.app.vo.myroom.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/myroom")
@Tag(name = "我的房间管理")
public class MyRoomController {

    @Autowired
    private LeaseAgreementService leaseAgreementService;
    
    @Autowired
    private RoomInfoService roomInfoService;
    
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    
    @Autowired
    private GraphInfoService graphInfoService;
    
    @Autowired
    private RepairRecordService repairRecordService;
    
    @Autowired
    private PaymentRecordService paymentRecordService;

    @GetMapping("info")
    @Operation(summary = "获取当前租住的房间信息")
    public Result<MyRoomInfoVo> getMyRoomInfo() {
        String phone = LoginUserHolder.getLoginUser().getUsername();
        
        // 查询用户当前有效的租约（状态为已签约或续约待确认）
        LambdaQueryWrapper<LeaseAgreement> agreementWrapper = new LambdaQueryWrapper<>();
        agreementWrapper.eq(LeaseAgreement::getPhone, phone)
                .in(LeaseAgreement::getStatus, 
                    LeaseStatus.SIGNED.getCode(), 
                    LeaseStatus.RENEWING.getCode())
                .eq(LeaseAgreement::getIsDeleted, 0)
                .orderByDesc(LeaseAgreement::getCreateTime)
                .last("limit 1");
        
        LeaseAgreement agreement = leaseAgreementService.getOne(agreementWrapper);
        if (agreement == null) {
            return Result.ok(null);
        }
        
        // 查询房间信息
        RoomInfo roomInfo = roomInfoService.getById(agreement.getRoomId());
        if (roomInfo == null) {
            return Result.ok(null);
        }
        
        // 组装返回数据
        MyRoomInfoVo vo = new MyRoomInfoVo();
        BeanUtils.copyProperties(roomInfo, vo);
        
        // 设置公寓名称和地址
        com.atguigu.lease.model.entity.ApartmentInfo apartmentInfo = apartmentInfoService.getById(roomInfo.getApartmentId());
        if (apartmentInfo != null) {
            vo.setApartmentName(apartmentInfo.getName());
            vo.setApartmentAddress(apartmentInfo.getAddressDetail());
        }
        
        // 设置房间图片
        LambdaQueryWrapper<com.atguigu.lease.model.entity.GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(com.atguigu.lease.model.entity.GraphInfo::getItemId, roomInfo.getId());
        graphWrapper.eq(com.atguigu.lease.model.entity.GraphInfo::getItemType, ItemType.ROOM);
        List<com.atguigu.lease.model.entity.GraphInfo> graphInfoList = graphInfoService.list(graphWrapper);
        List<GraphVo> graphVoList = graphInfoList.stream()
                .map(g -> new GraphVo(g.getName(), g.getUrl()))
                .collect(Collectors.toList());
        vo.setGraphVoList(graphVoList);
        
        // 设置租约信息
        vo.setLeaseStatus(agreement.getStatus().getCode());
        vo.setLeaseStartDate(agreement.getLeaseStartDate().toString());
        vo.setLeaseEndDate(agreement.getLeaseEndDate().toString());
        
        return Result.ok(vo);
    }

    @GetMapping("agreement")
    @Operation(summary = "获取当前租约详情")
    public Result<LeaseAgreement> getCurrentAgreement() {
        String phone = LoginUserHolder.getLoginUser().getUsername();
        
        LambdaQueryWrapper<LeaseAgreement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaseAgreement::getPhone, phone)
                .in(LeaseAgreement::getStatus, 
                    LeaseStatus.SIGNED.getCode(), 
                    LeaseStatus.RENEWING.getCode())
                .eq(LeaseAgreement::getIsDeleted, 0)
                .orderByDesc(LeaseAgreement::getCreateTime)
                .last("limit 1");
        
        return Result.ok(leaseAgreementService.getOne(wrapper));
    }

    @PostMapping("repair/save")
    @Operation(summary = "提交报修申请")
    public Result<Long> submitRepair(@RequestBody RepairSubmitVo submitVo) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        String phone = LoginUserHolder.getLoginUser().getUsername();
        
        // 查询用户的当前租约
        LambdaQueryWrapper<LeaseAgreement> agreementWrapper = new LambdaQueryWrapper<>();
        agreementWrapper.eq(LeaseAgreement::getPhone, phone)
                .in(LeaseAgreement::getStatus, 
                    LeaseStatus.SIGNED.getCode(), 
                    LeaseStatus.RENEWING.getCode())
                .eq(LeaseAgreement::getIsDeleted, 0)
                .orderByDesc(LeaseAgreement::getCreateTime)
                .last("limit 1");
        
        LeaseAgreement agreement = leaseAgreementService.getOne(agreementWrapper);
        if (agreement == null) {
            return Result.fail(201, "未找到有效的租约信息");
        }
        
        // 创建报修记录
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setUserId(userId);
        // 如果前端没有传roomId，自动使用当前有效租约的roomId
        repairRecord.setRoomId(submitVo.getRoomId() != null ? submitVo.getRoomId() : agreement.getRoomId());
        repairRecord.setAgreementId(agreement.getId());
        repairRecord.setType(submitVo.getType());
        repairRecord.setDescription(submitVo.getDescription());
        repairRecord.setContactPhone(submitVo.getContactPhone());
        repairRecord.setAppointmentTime(submitVo.getAppointmentTime());
        repairRecord.setStatus(1); // 待处理
        
        // 处理图片列表
        if (submitVo.getImages() != null && !submitVo.getImages().isEmpty()) {
            try {
                repairRecord.setImages(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(submitVo.getImages()));
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        
        repairRecordService.save(repairRecord);
        return Result.ok(repairRecord.getId());
    }

    @GetMapping("repair/list")
    @Operation(summary = "获取报修记录列表")
    public Result<Page<RepairRecordVo>> getRepairList(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        Page<RepairRecordVo> page = new Page<>(current, size);
        Page<RepairRecordVo> result = repairRecordService.pageRepairRecordByUserId(page, userId);
        return Result.ok(result);
    }

    @GetMapping("payment/list")
    @Operation(summary = "获取缴费记录列表")
    public Result<Page<PaymentRecordVo>> getPaymentList(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        Page<PaymentRecordVo> page = new Page<>(current, size);
        Page<PaymentRecordVo> result = paymentRecordService.pagePaymentRecordByUserId(page, userId);
        return Result.ok(result);
    }

    @GetMapping("payment/unpaid/total")
    @Operation(summary = "获取未缴费总额")
    public Result<BigDecimal> getUnpaidTotal() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        BigDecimal total = paymentRecordService.getUnpaidTotal(userId);
        return Result.ok(total);
    }
}