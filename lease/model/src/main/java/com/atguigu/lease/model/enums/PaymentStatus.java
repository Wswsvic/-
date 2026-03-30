package com.atguigu.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus implements BaseEnum {
    
    UNPAID(1, "未支付"),
    PAID(2, "已支付"),
    OVERDUE(3, "逾期");
    
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;
    
    PaymentStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @Override
    public Integer getCode() {
        return this.code;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}