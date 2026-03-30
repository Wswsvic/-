package com.atguigu.lease.web.app.vo.myroom;

import com.atguigu.lease.model.entity.PaymentRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "缴费记录VO")
public class PaymentRecordVo extends PaymentRecord {
    
    @Schema(description = "房间号")
    private String roomNumber;
    
    @Schema(description = "类型名称")
    private String typeName;
    
    @Schema(description = "状态名称")
    private String statusName;
}