<script>
import { RouterLink } from "vue-router";
</script>

<template>
  <header class="header-vertical">
    <nav class="header-vertical-mainNav">
      <div class="header-vertical-mainNav__logo">
        <router-link to="/enjoytrip">E</router-link>
      </div>
      <ul class="header-vertical-mainNav__menu__wrapper">
        <li class="header-vertical-mainNav__menu">
          <RouterLink :to="{ name: 'home' }">홈</RouterLink>
        </li>
        <li class="header-vertical-mainNav__menu">
          <RouterLink :to="{ name: 'search' }">검색</RouterLink>
        </li>
        <li class="header-vertical-mainNav__menu">
          <RouterLink :to="{ name: 'product' }">상품</RouterLink>
        </li>
        <li class="header-vertical-mainNav__menu">
          <RouterLink :to="{ name: 'hotplace' }">핫플</RouterLink>
        </li>
        <li class="header-vertical-mainNav__menu">
          <RouterLink :to="{ name: 'board' }">게시판</RouterLink>
        </li>
        <li class="header-vertical-mainNav__menu">
          <router-link to="/enjoytrip/login">
            <font-awesome-icon :icon="['fas', 'user']" />
          </router-link>
        </li>
      </ul>
    </nav>
  </header>
</template>

<style scoped>
.header-vertical {
  position: absolute;
  top: 0;
  left: 0;
  padding-top: 32px;
  width: 80px;
  height: 100%;
  background-color: var(--white);
  border-bottom: 1px solid var(--gray-light);
  box-shadow: 4px 0px 4px var(--gray-light);
  box-sizing: border-box;
  z-index: 3;
}

.header-vertical a {
  text-decoration: none;
  color: var(--black);
  font-weight: bold;
}

.header-vertical-mainNav__logo a {
  color: var(--primary);
  font-size: 32px;
  font-weight: 900;
}

.header-vertical-mainNav {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;

  margin: 0 auto;
  padding: 40 0px;

  width: 100%;
  height: 100%;
  max-height: 800px;
}

.header-vertical-mainNav__menu__wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-top: 32px;
}

.header-vertical-mainNav__menu {
  list-style: none;
  margin: 12px 0px;
}

.header-vertical-mainNav__menu a:hover {
  color: var(--primary);
}
</style>
