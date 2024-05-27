import { createRouter, createWebHistory } from "vue-router";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member.js";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value !== null || token === null) {
    if (!isValidToken.value || userInfo.value === null) {
      alert("로그인이 필요한 서비스입니다");
      next({ name: "memberLogin" });
    } else {
      next();
    }
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      redirect: "/enjoytrip",
    },
    {
      path: "/enjoytrip",
      name: "home",
      component: () => import("@/views/HomeView.vue"),
    },
    {
      path: "/enjoytrip/search",
      name: "search",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/SearchView.vue"),
    },
    {
      path: "/enjoytrip/board",
      name: "board",
      component: () => import("@/views/board/BoardListView.vue"),
    },
    {
      path: "/enjoytrip/board/regist",
      name: "boardRegist",
      component: () => import("@/views/board/BoardRegistView.vue"),
    },
    {
      path: "/enjoytrip/board/modify/:id",
      name: "boardModify",
      component: () => import("@/views/board/BoardModifyView.vue"),
    },
    {
      path: "/enjoytrip/board/:id",
      name: "boardDetail",
      component: () => import("@/views/board/BoardDetailView.vue"),
    },
    {
      path: "/enjoytrip/login",
      name: "memberLogin",
      component: () => import("@/views/member/MemberLoginView.vue"),
    },
    {
      path: "/enjoytrip/register",
      name: "memberRegister",
      component: () => import("@/views/member/MemberRegisterView.vue"),
    },
    {
      path: "/enjoytrip/mypage",
      name: "memberModify",
      component: () => import("@/views/member/MemberModifyView.vue"),
    },
    {
      path: "/enjoytrip/product",
      name: "product",
      component: () => import("@/views/product/ProductListView.vue"),
    },
    {
      path: "/enjoytrip/product/:id",
      name: "productDetail",
      component: () => import("@/views/product/ProductDetailView.vue"),
    },
    {
      path: "/enjoytrip/product/manage",
      name: "productManage",
      component: () => import("@/views/product/ProductManageView.vue"),
    },
    {
      path: "/enjoytrip/product/mypage",
      name: "productMypage",
      component: () => import("@/views/product/ProductMypageView.vue"),
    },
    {
      path: "/enjoytrip/product/regist",
      name: "productRegist",
      component: () => import("@/views/product/ProductRegistView.vue"),
    },
    {
      path: "/enjoytrip/product/modify/:id",
      name: "productModify",
      component: () => import("@/views/product/ProductModifyView.vue"),
    },
    {
      path: "/enjoytrip/hotplace",
      name: "hotplace",
      component: () => import("@/views/hotPlace/hotplaceListView.vue"),
    },
    {
      path: "/enjoytrip/hotplace/regist",
      name: "hotplaceRegist",
      component: () => import("@/views/hotPlace/hotplaceRegistView.vue"),
    },
    {
      path: "/enjoytrip/hotplace/modify/:id",
      name: "hotplaceModify",
      component: () => import("@/views/hotPlace/hotplaceModifyView.vue"),
    },
    {
      path: "/enjoytrip/hotplace/:id",
      name: "hotplaceDetail",
      component: () => import("@/views/hotPlace/hotplaceDetailView.vue"),
    },
    {
      path: "/enjoytrip/hotplace/my/:id",
      name: "hotplaceMyDetail",
      component: () => import("@/views/hotPlace/hotplaceMyDetailView.vue"),
    },
  ],
});

export default router;
