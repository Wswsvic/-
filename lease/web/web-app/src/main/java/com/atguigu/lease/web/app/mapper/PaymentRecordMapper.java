package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.PaymentRecord;
import com.atguigu.lease.web.app.vo.myroom.PaymentRecordVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {
    
    /**
     * 分页查询用户的缴费记录
     */
    Page<PaymentRecordVo> pagePaymentRecordByUserId(Page<PaymentRecordVo> page,
                                                     @Param("userId") Long userId);
    
    /**
     * 查询用户未缴费的金额汇总
     */
    java.math.BigDecimal selectUnpaidTotal(@Param("userId") Long userId);
}