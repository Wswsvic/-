<template>
  <div>
    <!--    顶部背景-->
    <van-image :src="bgImgUrl">
      <template v-slot:error>加载失败</template>
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
    </van-image>
    <!--    中间-->
    <div class="main-container flex justify-around mt-[15px]">
      <div
        v-for="item in navList"
        :key="item.path"
        class="flex flex-col justify-center items-center"
        @click="handleNav(item.path)"
      >
        <SvgIcon :name="item.icon" size="45" />
        <span>{{ item.name }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="MyRoom">
import { ref } from "vue";
import bgImgUrl from "@/assets/my_room_bg.png";
import { useRouter } from "vue-router";
import { getMyRoomInfo } from "@/api/myroom";
import { showToast } from "vant";

const router = useRouter();

const handleNav = async (path: string) => {
  if (path === "/repair") {
    try {
      const res = await getMyRoomInfo();
      if (!res.data || !res.data.id) {
        showToast("尚无租房，无法保修");
        return;
      }
      const address = `${res.data.apartmentName}${res.data.roomNumber}`;
      router.push({
        path,
        query: { roomId: res.data.id, roomAddress: address }
      });
    } catch (e) {
      showToast("获取房间信息失败");
    }
  } else {
    router.push(path);
  }
};

const navList = ref([
  {
    icon: "物业费用出账",
    name: "交房租",
    path: "/payment?type=rent"
  },
  {
    icon: "电费",
    name: "交电费",
    path: "/payment?type=electric"
  },
  {
    icon: "水费",
    name: "交水费",
    path: "/payment?type=water"
  },
  {
    icon: "物业报修",
    name: "报修",
    path: "/repair"
  }
]);
</script>
<style lang="less" scoped></style>
