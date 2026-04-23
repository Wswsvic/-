package com.atguigu.lease.model.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI客服对话记录实体
 *
 * @author AI重构学习
 * @since 2026-04-23
 */
@Data
@Schema(description = "AI客服对话记录")
@TableName("ai_conversation")
public class AiConversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID（租客）")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "用户类型：TENANT-租客")
    @TableField("user_type")
    private String userType;

    @Schema(description = "会话ID（用于关联多轮对话）")
    @TableField("session_id")
    private String sessionId;

    @Schema(description = "用户原始问题")
    @TableField("question")
    private String question;

    @Schema(description = "发送给AI的完整Prompt")
    @TableField("prompt")
    private String prompt;

    @Schema(description = "AI返回的答案")
    @TableField("answer")
    private String answer;

    @Schema(description = "AI提供商：OPENAI/AZURE/QIANFAN")
    @TableField("provider")
    private String provider;

    @Schema(description = "模型名称：gpt-3.5-turbo/gpt-4等")
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
