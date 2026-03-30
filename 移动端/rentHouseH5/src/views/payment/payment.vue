<template>
  <div class="payment-container min-h-screen bg-gray-100 pb-10">
    <van-nav-bar :title="title" left-arrow @click-left="onClickLeft" />

    <div class="unpaid-panel bg-white m-4 p-6 rounded-lg text-center shadow-sm">
      <div class="text-gray-500 mb-2">待缴金额 (元)</div>
      <div class="text-3xl font-bold text-red-500 mb-4">
        ¥{{ unpaidTotal.toFixed(2) }}
      </div>
      <van-button
        type="primary"
        block
        round
        @click="handlePay"
        :disabled="unpaidTotal <= 0"
        >立即缴费</van-button
      >
    </div>

    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="账单记录">
        <van-list
          v-model:loading="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
          class="p-4"
        >
          <div
            v-for="item in paymentList"
            :key="item.id"
            class="bg-white p-4 mb-3 rounded-lg shadow-sm flex justify-between items-center"
          >
            <div>
              <div class="font-bold text-lg mb-1">{{ item.paymentType }}</div>
              <div class="text-gray-400 text-sm">
                {{ item.createTime || item.paymentTime }}
              </div>
            </div>
            <div class="text-right">
              <div
                class="font-bold text-lg mb-1"
                :class="
                  item.status === 1 ? 'text-green-500' : 'text-orange-500'
                "
              >
                ¥{{ item.amount }}
              </div>
              <van-tag :type="item.status === 1 ? 'success' : 'warning'">
                {{ item.status === 1 ? "已支付" : "未支付" }}
              </van-tag>
            </div>
          </div>
        </van-list>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script setup lang="ts" name="Payment">
import { useRouter, useRoute } from "vue-router";
import { onMounted, ref } from "vue";
import { showSuccessToast } from "vant";
import { getPaymentList, getUnpaidTotal } from "@/api/myroom";
import type { PaymentRecordVo } from "@/api/myroom/types";

const router = useRouter();
const route = useRoute();
const title = ref("缴费中心");
const activeTab = ref(0);

const unpaidTotal = ref(0);
const loading = ref(false);
const finished = ref(false);
const current = ref(1);
const size = ref(10);
const paymentList = ref<PaymentRecordVo[]>([]);

onMounted(() => {
  const type = route.query.type;
  if (type === "rent") title.value = "交房租";
  else if (type === "electric") title.value = "交电费";
  else if (type === "water") title.value = "交水费";

  fetchTotal();
});

const fetchTotal = async () => {
  try {
    const res = await getUnpaidTotal();
    unpaidTotal.value = res.data || 0;
  } catch (error) {
    console.error(error);
  }
};

const onLoad = async () => {
  try {
    const res = await getPaymentList({
      current: current.value,
      size: size.value
    });
    const records = res.data.records || [];
    paymentList.value.push(...records);

    if (paymentList.value.length >= res.data.total) {
      finished.value = true;
    } else {
      current.value++;
    }
  } catch (error) {
    finished.value = true;
  } finally {
    loading.value = false;
  }
};

const handlePay = () => {
  showSuccessToast("由于缺乏收银台对接，模拟缴费成功！");
  unpaidTotal.value = 0;
  // 这里可以重新刷新列表
};

const onClickLeft = () => {
  router.back();
};
</script>
<style scoped lang="less"></style>
