package com.atguigu.lease.web.app.vo.myroom;

import com.atguigu.lease.model.entity.RoomInfo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "我的房间信息")
public class MyRoomInfoVo extends RoomInfo {
    
    @Schema(description = "公寓名称")
    private String apartmentName;
    
    @Schema(description = "公寓地址")
    private String apartmentAddress;
    
    @Schema(description = "房间图片列表")
    private List<GraphVo> graphVoList;
    
    @Schema(description = "房间标签")
    private List<String> labels;
    
    @Schema(description = "租约状态")
    private Integer leaseStatus;
    
    @Schema(description = "租约开始日期")
    private String leaseStartDate;
    
    @Schema(description = "租约结束日期")
    private String leaseEndDate;
}