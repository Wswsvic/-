<template>
  <div class="h-[100vh]">
    <van-nav-bar
      title="消息中心"
      placeholder
      safe-area-inset-top
    />
    <!--    消息列表-->
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="main-container flex flex-col min-h-[70vh]">
          <!--      单条消息-->
          <div
            class="flex items-start py-[10px] mt-[10px] bg-white rounded-xl px-2 shadow-sm"
            v-for="item in notificationList"
            :key="item.id"
            @click="handleRead(item)"
          >
            <div class="pt-[5px]">
              <van-icon v-if="item.isRead === 0" name="chat" color="#ee0a24" size="40" />
              <van-icon v-else name="chat-o" color="#999" size="40" />
            </div>

            <div class="flex flex-col justify-center ml-[10px] text-[14px] flex-1">
              <div class="flex justify-between mb-[3px] items-center">
                <div class="font-bold text-[15px]" :class="{ 'text-gray-500': item.isRead === 1 }">{{ item.title || '系统通知' }}</div>
                <div class="text-[12px] text-gray-400">{{ formatTime(item.createTime) }}</div>
              </div>

              <div class="text-gray-500 text-[13px] mt-1 leading-normal">
                {{ item.content }}
              </div>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script setup lang="ts" name="Message">
import { ref } from "vue";
import { getNotificationPage, markRead } from "@/api/notification";
import { Toast } from "vant";

const notificationList = ref<any[]>([]);
const refreshing = ref(false);
const loading = ref(false);
const finished = ref(false);

const current = ref(1);
const size = ref(10);

const formatTime = (timeStr: string) => {
  if (!timeStr) return '';
  // 简易时间格式化
  const date = new Date(timeStr);
  return `${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
};

const fetchList = async () => {
  try {
    const res = await getNotificationPage({
      current: current.value,
      size: size.value
    });
    const records = res.data.records || [];
    if (refreshing.value) {
      notificationList.value = records;
      refreshing.value = false;
    } else {
      notificationList.value.push(...records);
    }

    loading.value = false;

    if (records.length < size.value) {
      finished.value = true;
    } else {
      current.value++;
    }
  } catch (e) {
    loading.value = false;
    refreshing.value = false;
    finished.value = true;
    console.error('获取消息失败', e);
  }
};

const onLoad = () => {
  fetchList();
};

const onRefresh = () => {
  finished.value = false;
  current.value = 1;
  loading.value = true;
  onLoad();
};

const handleRead = async (item: any) => {
  if (item.isRead === 0) {
    // 标记为已读
    await markRead([item.id]);
    item.isRead = 1;
  }
  // TODO: 后续可以根据 item.businessType & extraData 进行前端页面跳转，目前仅作状态翻转
};
</script>
<style lang="less" scoped></style>
