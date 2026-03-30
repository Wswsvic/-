package com.atguigu.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RepairType implements BaseEnum {
    
    WATER_ELECTRICITY(1, "水电问题"),
    FURNITURE(2, "家具问题"),
    APPLIANCE(3, "家电问题"),
    NETWORK(4, "网络问题"),
    OTHER(5, "其他");
    
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;
    
    RepairType(Integer code, String name) {
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