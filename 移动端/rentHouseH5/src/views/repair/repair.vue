<template>
  <div class="repair-container min-h-screen bg-gray-100 pb-10">
    <van-nav-bar title="物业报修" left-arrow @click-left="onClickLeft" />

    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="申请报修">
        <van-form @submit="onSubmit" class="mt-4">
          <van-cell-group inset>
            <van-field
              v-model="typeName"
              is-link
              readonly
              name="type"
              label="报修类型"
              placeholder="点击选择报修类型"
              @click="showTypePicker = true"
              :rules="[{ required: true, message: '请选择报修类型' }]"
            />
            <van-popup v-model:show="showTypePicker" position="bottom">
              <van-picker
                :columns="typeColumns"
                @confirm="onConfirmType"
                @cancel="showTypePicker = false"
              />
            </van-popup>

            <van-field
              v-model="roomAddress"
              name="roomAddress"
              label="房屋地址"
              placeholder="获取中..."
              readonly
              :rules="[{ required: true, message: '房屋地址不能为空' }]"
            />

            <van-field
              v-model="formData.contactPhone"
              name="contactPhone"
              label="联系电话"
              placeholder="请输入联系电话"
              :rules="[{ required: true, message: '请输入联系电话' }]"
            />

            <!-- 简单起见，这里直接让用户选日期格式，或者使用 datetime-picker -->
            <van-field
              v-model="formData.appointmentTime"
              name="appointmentTime"
              label="期望时间"
              placeholder="如：2026-04-01 14:00"
              :rules="[{ required: true, message: '请输入期望上门时间' }]"
            />

            <van-field
              v-model="formData.description"
              rows="3"
              autosize
              label="问题描述"
              type="textarea"
              placeholder="请详细描述需要报修的问题"
              :rules="[{ required: true, message: '请输入问题描述' }]"
              show-word-limit
              maxlength="200"
            />
          </van-cell-group>
          <div class="p-4 text-xs text-gray-400">
            目前移动端仅支持文字报修，如需上传图片可由后台补录。
          </div>
          <div style="margin: 16px">
            <van-button round block type="primary" native-type="submit">
              提交申请
            </van-button>
          </div>
        </van-form>
      </van-tab>

      <van-tab title="报修记录">
        <van-list
          v-model:loading="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
          class="p-4"
        >
          <div
            v-for="item in recordList"
            :key="item.id"
            class="bg-white p-4 mb-3 rounded-lg shadow-sm"
          >
            <div class="flex justify-between items-center mb-2 border-b pb-2">
              <div class="font-bold border-l-4 border-blue-500 pl-2">
                {{ getTypeName(item.type) }}
              </div>
              <van-tag :type="getStatusType(item.status)">{{
                getStatusText(item.status)
              }}</van-tag>
            </div>
            <div class="text-gray-600 text-sm mb-1">
              预约时间：{{ item.appointmentTime }}
            </div>
            <div class="text-gray-600 text-sm mb-1 mt-2">
              问题描述：{{ item.description }}
            </div>
            <div class="text-gray-400 text-xs mt-3 text-right">
              提交于 {{ item.createTime }}
            </div>
          </div>
        </van-list>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script setup lang="ts" name="Repair">
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { showSuccessToast, showFailToast } from "vant";
import { submitRepairStatus, getRepairList } from "@/api/myroom";
import type { RepairSubmitVo, RepairRecordVo } from "@/api/myroom/types";

const router = useRouter();
const route = useRoute();
const activeTab = ref(0);

// 表单相关
const showTypePicker = ref(false);
const typeName = ref("");
const roomAddress = ref("");
const typeColumns = [
  { text: "水电问题", value: 1 },
  { text: "家具问题", value: 2 },
  { text: "家电问题", value: 3 },
  { text: "网络问题", value: 4 },
  { text: "其他", value: 5 }
];

const formData = ref<RepairSubmitVo>({
  roomId: undefined,
  type: undefined,
  description: "",
  contactPhone: "",
  appointmentTime: "",
  images: []
});

onMounted(() => {
  if (route.query.roomId) {
    formData.value.roomId = Number(route.query.roomId);
  }
  if (route.query.roomAddress) {
    roomAddress.value = route.query.roomAddress as string;
  }
});

const onConfirmType = ({ selectedOptions }: any) => {
  typeName.value = selectedOptions[0]?.text;
  formData.value.type = selectedOptions[0]?.value;
  showTypePicker.value = false;
};

const onSubmit = async () => {
  try {
    await submitRepairStatus(formData.value);
    showSuccessToast("提交成功");
    // 跳转到记录tab并刷新
    activeTab.value = 1;
    onRefresh();
  } catch (error) {
    showFailToast("提交失败");
  }
};

// 列表相关
const loading = ref(false);
const finished = ref(false);
const current = ref(1);
const size = ref(10);
const recordList = ref<RepairRecordVo[]>([]);

const onLoad = async () => {
  try {
    const res = await getRepairList({
      current: current.value,
      size: size.value
    });
    const records = res.data.records || [];
    recordList.value.push(...records);

    if (recordList.value.length >= res.data.total) {
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

const getTypeName = (type: any) => {
  // 如果后端传了 type 或者是对象、数字，进行格式化
  if (typeof type === "number" || typeof type === "string") {
    const t = typeColumns.find(col => col.value == type);
    return t ? t.text : "其他";
  }
  // 防止后端直接返回带有 getName 的枚举或者已经有 text
  if (type && type.name) return type.name;
  return "未知";
};

const onRefresh = () => {
  finished.value = false;
  current.value = 1;
  recordList.value = [];
  onLoad();
};

const getStatusText = (status: number) => {
  if (status === 1) return "待处理";
  if (status === 2) return "处理中";
  if (status === 3) return "已完成";
  return "未知";
};

const getStatusType = (status: number) => {
  if (status === 1) return "warning";
  if (status === 2) return "primary";
  if (status === 3) return "success";
  return "default";
};

const onClickLeft = () => {
  router.back();
};
</script>
<style scoped lang="less"></style>
