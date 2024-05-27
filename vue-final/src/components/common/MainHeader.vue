<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

// 스토어 가져오기
const memberStore = useMemberStore();
const { isLogin, userId, isHost } = storeToRefs(memberStore);
const { userLogout } = memberStore;

// 프로필 열기 상태
const isProfileOpen = ref(false);
const setProfileOpen = () => {
  isProfileOpen.value = !isProfileOpen.value;
};

// 사이드바 열기 상태
const isSidebarOpen = ref(false);
const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

//userLogout
const logout = async () => {
  await userLogout();
};

onMounted(() => {
  console.log(isHost);
});
</script>

<template>
  <header class="header">
    <nav class="header-mainNav">
      <div class="header-group-small">
        <div class="menu-btn-small" @click="toggleSidebar">
          <font-awesome-icon :icon="['fas', 'bars']" />
        </div>
      </div>
      <div class="header-logo-group-small">
        <div class="header-mainNav__logo-small">
          <router-link to="/enjoytrip">E</router-link>
        </div>
      </div>
      <div class="header-group-large">
        <div class="header-mainNav__logo">
          <router-link to="/enjoytrip">EnjoyTrip</router-link>
        </div>
        <ul class="header-mainNav__menu__wrapper">
          <li class="header-mainNav__menu">
            <RouterLink :to="{ name: 'home' }">홈</RouterLink>
          </li>
          <li class="header-mainNav__menu">
            <RouterLink :to="{ name: 'search' }">검색</RouterLink>
          </li>
          <li class="header-mainNav__menu">
            <RouterLink :to="{ name: 'product' }">상품</RouterLink>
          </li>
          <li class="header-mainNav__menu">
            <RouterLink :to="{ name: 'hotplace' }">핫플레이스</RouterLink>
          </li>
          <li class="header-mainNav__menu">
            <RouterLink :to="{ name: 'board' }">게시판</RouterLink>
          </li>
        </ul>
      </div>
      <ul class="header-mainNav__login-menu__wrapper">
        <li @click="setProfileOpen" class="header-mainNav__login-menu">
          <span class="icon-bars"><font-awesome-icon :icon="['fas', 'bars']" /></span>
          <span class="user-name" v-if="isLogin">{{ userId }}</span>
          <span class="icon-user"><font-awesome-icon :icon="['fas', 'user']" /></span>
        </li>
        <div v-if="isProfileOpen" class="header-mainNav__login-sub-menu">
          <ul>
            <RouterLink v-if="!isLogin" :to="{ name: 'memberLogin' }"><li>로그인</li></RouterLink>
            <RouterLink v-if="isLogin" :to="{ name: 'home' }" @click="logout">
              <li>로그아웃</li>
            </RouterLink>
            <RouterLink :to="{ name: 'memberRegister' }"><li>회원가입</li></RouterLink>
            <RouterLink v-if="isLogin" :to="{ name: 'memberModify' }"
              ><li>마이페이지</li></RouterLink
            >
            <RouterLink v-if="isLogin && isHost" :to="{ name: 'productManage' }"
              ><li>상품관리</li></RouterLink
            >
            <RouterLink v-if="isLogin" :to="{ name: 'productMypage' }"
              ><li>나의예약</li></RouterLink
            >
          </ul>
        </div>
      </ul>
    </nav>

    <div v-if="isSidebarOpen" class="overlay" @click="toggleSidebar"></div>

    <div class="sidebar" :class="{ open: isSidebarOpen }">
      <ul class="sidebar__menu">
        <li class="sidebar__menu-item">
          <RouterLink :to="{ name: 'home' }" @click="toggleSidebar">홈</RouterLink>
        </li>
        <li class="sidebar__menu-item">
          <RouterLink :to="{ name: 'search' }" @click="toggleSidebar">검색</RouterLink>
        </li>
        <li class="sidebar__menu-item">
          <RouterLink :to="{ name: 'product' }" @click="toggleSidebar">상품</RouterLink>
        </li>
        <li class="sidebar__menu-item">
          <RouterLink :to="{ name: 'hotplace' }" @click="toggleSidebar">핫플레이스</RouterLink>
        </li>
        <li class="sidebar__menu-item">
          <RouterLink :to="{ name: 'board' }" @click="toggleSidebar">게시판</RouterLink>
        </li>
      </ul>
    </div>
  </header>
</template>

<style scoped>
.header {
  position: sticky;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px;
  background-color: var(--white);
  border-bottom: 1px solid var(--gray-light);
  box-shadow: 0px 4px 4px var(--gray-light);
  box-sizing: border-box;
  z-index: 3;
}

.header a {
  text-decoration: none;
  color: var(--black);
  font-weight: bold;
}

.menu-btn-small {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  list-style: none;
  border: 1px solid var(--gray-light);
  border-radius: 10px;
  font-size: 18px;
  cursor: pointer;
}

.menu-btn-small:hover {
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
}

.header-group-small {
  display: flex;
  width: 120px;
}

.header-logo-group-small {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
}

.header-mainNav__logo-small a {
  color: var(--primary);
  font-size: 32px;
  font-weight: 900;
}

.header-group-large {
  display: none;
}

.header-mainNav__logo {
  width: 150px;
}

.header-mainNav__logo a {
  color: var(--primary);
  font-size: 32px;
  font-weight: 900;
}

.header-mainNav {
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin: 0 auto;
  padding: 0 40px;

  width: 100%;
  max-width: 1200px;
  height: 100%;
}

.header-mainNav.fullNav {
  max-width: 80px;
}

.header-mainNav__menu__wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 40px;
}

.header-mainNav__menu {
  list-style: none;
  margin: 0px 12px;
}

.header-mainNav__menu a:hover {
  color: var(--primary);
}

.header-mainNav__login-menu__wrapper {
  position: relative;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 120px;
}

.header-mainNav__login-menu {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 42px;
  border: 1px solid var(--gray-light);
  border-radius: 20px;
  padding: 0px 16px;
  text-align: center;
  list-style: none;
  cursor: pointer;
}

.header-mainNav__login-menu:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 효과 추가 */
}

.header-mainNav__login-menu a:hover {
  color: var(--primary);
}

.header-mainNav__login-sub-menu {
  position: absolute;
  top: 48px;
  right: 0;
  width: 160px;
  border: 1px solid var(--gray-light);
  border-radius: 8px;
  background-color: var(--white);
  z-index: 4;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 효과 추가 */
}

.header-mainNav__login-sub-menu li {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-left: 16px;
  list-style: none;
  height: 48px;
  font-size: 16px;
}

.header-mainNav__login-sub-menu li:hover {
  color: var(--primary);
}

.icon-bars {
  color: var(--gray-medium);
  margin-right: 16px;
  font-size: 18px;
}

.icon-user {
  color: var(--gray-medium);
  font-size: 18px;
}

.user-name {
  color: var(--gray-medium);
  font-size: 14px;
  margin-right: 8px;
  white-space: nowrap;
}

/* Sidebar Styles */
.sidebar {
  position: fixed;
  top: 0;
  left: -250px;
  width: 250px;
  height: 100%;
  background-color: var(--white);
  border-right: 1px solid var(--gray-light);
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
  z-index: 4;
  transition: left 0.3s ease-in-out;
}

.sidebar.open {
  left: 0;
}

.sidebar__menu {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar__menu-item {
  padding: 16px;
  border-bottom: 1px solid var(--gray-light);
}

.sidebar__menu-item a {
  color: var(--black);
  text-decoration: none;
  font-size: 18px;
}

.sidebar__menu-item a:hover {
  color: var(--primary);
}

/* Overlay Styles */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 3;
}

@media (min-width: 768px) {
  .header-group-small {
    display: none;
  }

  .header-group-large {
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }

  .header-mainNav__login-menu__wrapper {
    width: 160px;
  }

  .header-logo-group-small {
    display: none;
  }
}
</style>
