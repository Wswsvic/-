package com.atguigu.lease.web.app.controller.notification;

import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.Notification;
import com.atguigu.lease.web.app.service.NotificationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "消息通知管理")
@RestController
@RequestMapping("/app/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "分页获取消息通知")
    @GetMapping("/page")
    public Result<Page<Notification>> page(@RequestParam long current, @RequestParam long size,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false) Integer isRead) {
        Long userId = com.atguigu.lease.common.login.LoginUserHolder.getLoginUser().getUserId();
        LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getUserType, 1);
        if (type != null) {
            queryWrapper.eq(Notification::getType, type);
        }
        if (isRead != null) {
            queryWrapper.eq(Notification::getIsRead, isRead);
        }
        queryWrapper.orderByDesc(Notification::getCreateTime);

        Page<Notification> page = new Page<>(current, size);
        Page<Notification> result = notificationService.page(page, queryWrapper);
        return Result.ok(result);
    }

    @Operation(summary = "获取未读消息数量")
    @GetMapping("/unread/count")
    public Result<Long> getUnreadCount() {
        Long userId = com.atguigu.lease.common.login.LoginUserHolder.getLoginUser().getUserId();
        LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getUserType, 1)
                .eq(Notification::getIsRead, 0);
        long count = notificationService.count(queryWrapper);
        return Result.ok(count);
    }

    @Operation(summary = "标记消息为已读")
    @PutMapping("/read")
    public Result<Void> markRead(@RequestBody(required = false) Long[] notificationIds) {
        Long userId = com.atguigu.lease.common.login.LoginUserHolder.getLoginUser().getUserId();
        if (notificationIds != null && notificationIds.length > 0) {
            for (Long id : notificationIds) {
                Notification notification = new Notification();
                notification.setId(id);
                notification.setIsRead(1);
                notificationService.updateById(notification);
            }
        } else {
            LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Notification::getUserId, userId)
                    .eq(Notification::getUserType, 1)
                    .eq(Notification::getIsRead, 0);
            Notification notification = new Notification();
            notification.setIsRead(1);
            notificationService.update(notification, queryWrapper);
        }
        return Result.ok();
    }
}


