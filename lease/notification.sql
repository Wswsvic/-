use shangting;

CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '接收用户ID（租客或管理员）',
  `user_type` tinyint(4) NOT NULL COMMENT '用户类型：1-租客，2-管理员',
  `type` tinyint(4) NOT NULL COMMENT '通知类型：1-签约，2-退租，3-续约，4-租约到期，5-系统公告',
  `business_id` bigint(20) NOT NULL COMMENT '业务ID（如租约ID）',
  `business_type` tinyint(4) NOT NULL COMMENT '业务类型：1-租约，2-看房预约，3-房间',
  `title` varchar(100) NOT NULL COMMENT '通知标题',
  `content` varchar(500) NOT NULL COMMENT '通知内容',
  `is_read` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读：0-未读，1-已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `extra_data` json DEFAULT NULL COMMENT '扩展数据，JSON格式，存储跳转所需的额外参数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_type` (`user_id`, `user_type`),
  KEY `idx_user_read` (`user_id`, `is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户通知消息表';

INSERT INTO `notification` (`user_id`, `user_type`, `type`, `business_id`, `business_type`, `title`, `content`, `is_read`)
VALUES
(1, 1, 1, 1001, 1, '签约待确认', '您有一份新的租约合同待确认签署，房间：201，公寓：尚庭公寓', 0),
(1, 1, 5, 0, 0, '系统升级通知', '今晚凌晨2点系统将进行升级，预计停机维护2小时。', 0),
(1, 2, 2, 1002, 1, '退租申请', '用户张三提交了退租申请，请尽快审核。', 0);

