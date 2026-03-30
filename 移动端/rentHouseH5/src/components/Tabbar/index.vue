<template>
  <van-tabbar
    v-show="isShowTabBar"
    v-model="active"
    :placeholder="true"
    :route="true"
    fixed
  >
    <van-tabbar-item
      v-for="(item, index) in tabBarData"
      :key="index"
      :icon="item?.icon as string"
      :to="item.to"
      :badge="item.to.path === '/message' && unreadCount > 0 ? unreadCount : ''"
    >
      {{ item.title }}
    </van-tabbar-item>
  </van-tabbar>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from "vue";
import tabBarRoutes from "@/router/tabBarRoutes";
import { useRoute } from "vue-router";
import { getUnreadCount } from "@/api/notification";
import { useUserStore } from "@/store/modules/user";

const route = useRoute();
const active = ref(0);
const unreadCount = ref(0);

const fetchUnreadCount = async () => {
  const userStore = useUserStore();
  if (userStore.token) {
    try {
      const res = await getUnreadCount();
      unreadCount.value = res.data || 0;
    } catch (e) {
      console.error(e);
    }
  }
};

watch(() => route.path, () => {
  // 每次路由变化如果是到了根级别页面，重新获取一下未读数
  if (isShowTabBar.value) {
    fetchUnreadCount();
  }
});

onMounted(() => {
  fetchUnreadCount();
});
// const tabBarData = reactive([
//   {
//     icon: "search",
//     title: "找房",
//     to: {
//       path: "/search"
//     }
//   },
//   {
//     icon: "star-o",
//     title: "圈子",
//     to: {
//       path: "/group"
//     }
//   },
//   {
//     icon: "home-o",
//     title: "我的房间",
//     to: {
//       path: "/myRoom"
//     }
//   },
//   {
//     icon: "comment-o",
//     title: "消息",
//     to: {
//       path: "/message"
//     }
//   },
//   {
//     icon: "user-o",
//     title: "个人中心",
//     to: {
//       path: "/userCenter"
//     }
//   }
//   // {
//   //   icon: "user-o",
//   //   title: "关于",
//   //   to: {
//   //     path: "/about"
//   //   }
//   // }
// ]);
const tabBarData = computed(() => {
  return tabBarRoutes.map(item => {
    return {
      icon: item.meta?.icon,
      title: item.meta?.title,
      to: {
        path: item.path
      }
    };
  });
});
const isShowTabBar = computed(() => {
  return tabBarRoutes.some(item => item.path === route.path);
});
</script>
