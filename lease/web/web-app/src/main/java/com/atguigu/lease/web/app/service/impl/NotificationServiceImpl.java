package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.Notification;
import com.atguigu.lease.web.app.mapper.NotificationMapper;
import com.atguigu.lease.web.app.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
}

