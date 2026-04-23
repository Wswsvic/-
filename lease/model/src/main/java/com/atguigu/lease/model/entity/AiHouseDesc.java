// lease/model/src/main/java/com/atguigu/lease/model/entity/AiHouseDesc.java
package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI房源文案生成记录实体
 * 
 * @author AI重构学习
 * @since 2026-04-23
 */
@Data
@Schema(description = "AI房源文案生成记录")
@TableName("ai_house_desc")
public class AiHouseDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "管理员ID")
    @TableField("admin_id")
    private Long adminId;

    @Schema(description = "关联房源ID")
    @TableField("room_id")
    private Long roomId;

    @Schema(description = "用户输入的关键词")
    @TableField("keywords")
    private String keywords;

    @Schema(description = "文案风格：PROFESSIONAL-专业，WARM-温馨")
    @TableField("tone")
    private String tone;

    @Schema(description = "期望文案最大长度")
    @TableField("max_length")
    private Integer maxLength;

    @Schema(description = "发送给AI的完整Prompt")
    @TableField("prompt")
    private String prompt;

    @Schema(description = "AI生成的房源描述文案")
    @TableField("generated_desc")
    private String generatedDesc;

    @Schema(description = "AI提供商")
    @TableField("provider")
    private String provider;

    @Schema(description = "模型名称")
    @TableField("model")
    private String model;

    @Schema(description = "接口耗时（毫秒）")
    @TableField("latency_ms")
    private Integer latencyMs;

    @Schema(description = "消耗Token数")
    @TableField("token_used")
    private Integer tokenUsed;

    @Schema(description = "状态：SUCCESS-成功，FALLBACK-降级，ERROR-失败")
    @TableField("status")
    private String status;

    @Schema(description = "错误信息")
    @TableField("error_msg")
    private String errorMsg;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除：0-未删除，1-已删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;
}