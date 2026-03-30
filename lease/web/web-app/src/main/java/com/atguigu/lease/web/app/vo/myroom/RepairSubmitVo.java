package com.atguigu.lease.web.app.vo.myroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "提交报修VO")
public class RepairSubmitVo {
    
    @Schema(description = "房间ID", required = true)
    private Long roomId;
    
    @Schema(description = "报修类型", required = true)
    private Integer type;
    
    @Schema(description = "问题描述", required = true)
    private String description;
    
    @Schema(description = "图片列表")
    private List<String> images;
    
    @Schema(description = "联系电话")
    private String contactPhone;
    
    @Schema(description = "预约时间")
    private Date appointmentTime;
}