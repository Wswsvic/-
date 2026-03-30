package com.atguigu.lease.web.app.vo.myroom;

import com.atguigu.lease.model.entity.RepairRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "报修记录VO")
public class RepairRecordVo extends RepairRecord {
    
    @Schema(description = "房间号")
    private String roomNumber;
    
    @Schema(description = "公寓名称")
    private String apartmentName;
    
    @Schema(description = "报修类型名称")
    private String typeName;
    
    @Schema(description = "状态名称")
    private String statusName;
}