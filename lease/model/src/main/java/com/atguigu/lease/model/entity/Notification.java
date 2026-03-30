package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "notification", autoResultMap = true)
@Schema(description = "用户通知消息表")
public class Notification extends BaseEntity {

    @Schema(description = "接收用户ID（租客或管理员）")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "用户类型：1-租客，2-管理员")
    @TableField("user_type")
    private Integer userType;

    @Schema(description = "通知类型：1-签约，2-退租，3-续约，4-租约到期，5-系统公告")
    @TableField("type")
    private Integer type;

    @Schema(description = "业务ID")
    @TableField("business_id")
    private Long businessId;

    @Schema(description = "业务类型：1-租约，2-看房预约，3-房间")
    @TableField("business_type")
    private Integer businessType;

    @Schema(description = "通知标题")
    @TableField("title")
    private String title;

    @Schema(description = "通知内容")
    @TableField("content")
    private String content;

    @Schema(description = "是否已读：0-未读，1-已读")
    @TableField("is_read")
    private Integer isRead;

    @Schema(description = "阅读时间")
    @TableField("read_time")
    private Date readTime;

    @Schema(description = "扩展数据")
    @TableField(value = "extra_data", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private String extraData;
}



