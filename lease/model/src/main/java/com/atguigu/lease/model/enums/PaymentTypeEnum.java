package com.atguigu.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentTypeEnum implements BaseEnum {
    
    RENT(1, "租金"),
    DEPOSIT(2, "押金"),
    UTILITY(3, "水电费"),
    PROPERTY(4, "物业费"),
    OTHER(5, "其他");
    
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;
    
    PaymentTypeEnum(Integer code, String name) {
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