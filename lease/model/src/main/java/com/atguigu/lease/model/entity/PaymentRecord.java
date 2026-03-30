package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("payment_record")
@Schema(description = "缴费记录")
public class PaymentRecord extends BaseEntity {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "租约ID")
    private Long agreementId;
    
    @Schema(description = "房间ID")
    private Long roomId;
    
    @Schema(description = "类型")
    private Integer type;
    
    @Schema(description = "金额")
    private BigDecimal amount;
    
    @Schema(description = "状态")
    private Integer status;
    
    @Schema(description = "截止日期")
    private Date dueDate;
    
    @Schema(description = "支付时间")
    private Date payTime;
    
    @Schema(description = "支付方式")
    private String payMethod;
    
    @Schema(description = "交易流水号")
    private String transactionId;
}