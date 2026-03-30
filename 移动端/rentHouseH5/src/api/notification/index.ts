import http from '@/utils/http';

// 获取分页消息通知
export function getNotificationPage(data: any) {
  return http.get<any>('/app/notification/page', data);
}

// 获取未读消息数量
export function getUnreadCount() {
  return http.get<number>('/app/notification/unread/count');
}

// 标记消息为已读
export function markRead(data: any[]) {
  return http.put<any>('/app/notification/read', data);
}

