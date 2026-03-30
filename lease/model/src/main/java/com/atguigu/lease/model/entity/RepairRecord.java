package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("repair_record")
@Schema(description = "报修记录")
public class RepairRecord extends BaseEntity {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "房间ID")
    private Long roomId;
    
    @Schema(description = "租约ID")
    private Long agreementId;
    
    @Schema(description = "报修类型")
    private Integer type;
    
    @Schema(description = "问题描述")
    private String description;
    
    @Schema(description = "图片列表JSON")
    private String images;
    
    @Schema(description = "状态")
    private Integer status;
    
    @Schema(description = "联系电话")
    private String contactPhone;
    
    @Schema(description = "预约时间")
    private Date appointmentTime;
    
    @Schema(description = "处理时间")
    private Date handleTime;
    
    @Schema(description = "处理结果")
    private String handleResult;
}