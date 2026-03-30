package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.Notification;
import com.atguigu.lease.web.admin.mapper.NotificationMapper;
import com.atguigu.lease.web.admin.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
}
