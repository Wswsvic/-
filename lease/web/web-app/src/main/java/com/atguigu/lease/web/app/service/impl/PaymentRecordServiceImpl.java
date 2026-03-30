package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.PaymentRecord;
import com.atguigu.lease.web.app.mapper.PaymentRecordMapper;
import com.atguigu.lease.web.app.service.PaymentRecordService;
import com.atguigu.lease.web.app.vo.myroom.PaymentRecordVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentRecordServiceImpl 
    extends ServiceImpl<PaymentRecordMapper, PaymentRecord> 
    implements PaymentRecordService {
    
    @Override
    public Page<PaymentRecordVo> pagePaymentRecordByUserId(Page<PaymentRecordVo> page, Long userId) {
        return baseMapper.pagePaymentRecordByUserId(page, userId);
    }
    
    @Override
    public BigDecimal getUnpaidTotal(Long userId) {
        return baseMapper.selectUnpaidTotal(userId);
    }
}