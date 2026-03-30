package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.PaymentRecord;
import com.atguigu.lease.web.app.vo.myroom.PaymentRecordVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

public interface PaymentRecordService extends IService<PaymentRecord> {
    
    /**
     * 分页查询用户缴费记录
     */
    Page<PaymentRecordVo> pagePaymentRecordByUserId(Page<PaymentRecordVo> page, Long userId);
    
    /**
     * 查询用户未缴费总额
     */
    BigDecimal getUnpaidTotal(Long userId);
}