<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { ref, computed, onMounted, useTransitionState } from "vue";
import { RouterLink, useRoute } from "vue-router";
import axios from "axios";

import { localAuthAxios } from "@/utils/http-commons";
const localAuth = localAuthAxios();

// 스토어 가져오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

// 페이지 이동을 위한 변수와 메서드들
const totalPages = ref(1); // 총 페이지 수
const currentPage = ref(1); // 현재 페이지
const pageSize = 10;

const prevPage = () => {
  currentPage.value = Math.max(1, currentPage.value - 10);
};

const nextPage = () => {
  currentPage.value = Math.min(currentPage.value + 10, totalPages.value);
};

const goToPage = (pageNumber) => {
  currentPage.value = pageNumber;
  getPosts();
};

// 표시할 페이지 버튼들을 계산하는 computed 속성
const displayedPages = computed(() => {
  const startPage = Math.floor((currentPage.value - 1) / pageSize) * pageSize + 1;
  const endPage = Math.min(totalPages.value, startPage + pageSize - 1);
  return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
});

//검색
const keyword = ref("");
const searchPost = async () => {
  try {
    const response = await axios.get(
      `http://localhost:80/board?pageNo=${currentPage.value}&word=${keyword.value}`
    ); // 서버에서 데이터 가져오기
    console.log(response.data);
    posts.value = response.data.boards; // 데이터를 변수에 저장
    totalPages.value = response.data.page.total; //페이지네이션을 위한 totalPage 설정
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};
///////////////////////////////////////////////
//mount시에 postId에 해당하는 게시물 정보가져오기
const posts = ref([]);
const getPosts = async () => {
  try {
    const response = await axios.get(`http://localhost:80/board?pageNo=${currentPage.value}`); // 서버에서 데이터 가져오기
    console.log(response.data);
    posts.value = response.data.boards; // 데이터를 변수에 저장
    totalPages.value = response.data.page.total; //페이지네이션을 위한 totalPage 설정
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const auth = ref(false);
onMounted(() => {
  console.log("mounted!!!!!");
  auth.value = !(userId?.value === null);
  getPosts();
});
</script>

<template>
  <MainHeader></MainHeader>
  <div class="container">
    <div class="wrapper">
      <h2 class="title">문의 게시판</h2>
    </div>
    <div class="wrapper">
      <div class="inner-wrapper">
        <RouterLink :to="{ name: 'boardRegist' }">
          <button v-if="auth" class="regist_btn">글쓰기</button>
        </RouterLink>
        <div class="search-bar">
          <input v-model="keyword" />
          <button @click="searchPost" type="button" class="search-bar_button">
            <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
          </button>
        </div>
      </div>
    </div>
    <div class="wrapper">
      <RouterLink
        v-for="(post, index) in posts"
        :key="post.boardId"
        :to="{ name: 'boardDetail', params: { id: post.boardId } }"
      >
        <div :class="{ post: true, 'first-post': index === 0 }">
          <p class="post-title">{{ post.title }}</p>
          <p class="post-author">{{ post.userId }}</p>
          <p class="post-author">{{ post.createdAt.split("T")[0] }}</p>
        </div>
      </RouterLink>
    </div>
    <div class="pagination">
      <button v-if="currentPage > 10" @click="prevPage" class="pagination_btn light_btn">
        <font-awesome-icon :icon="['fas', 'angle-left']" />
      </button>
      <button
        v-for="pageNumber in displayedPages"
        :key="pageNumber"
        @click="goToPage(pageNumber)"
        class="pagination_btn"
        :class="{
          active_btn: pageNumber === currentPage,
          nonActive_btn: pageNumber !== currentPage,
        }"
      >
        {{ pageNumber }}
      </button>
      <button
        v-if="currentPage + 10 <= totalPages"
        @click="nextPage"
        class="pagination_btn light_btn"
      >
        <font-awesome-icon :icon="['fas', 'angle-right']" />
      </button>
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  color: inherit;
}

a:visited {
  text-decoration: none;
  color: inherit;
}

.container {
  width: 100%;
  min-height: calc(100vh - 80px - 200px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 60px 0px;
}

.wrapper {
  width: 600px;
  margin-bottom: 36px;
}

.inner-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  font-weight: bold;
  width: 100%;
}

.regist_btn {
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 10px 24px;
  width: 100px;
  height: 38px;

  background-color: var(--secondary);
  border: none;
  border-radius: 10px;
  font-size: 16px;
  font-weight: bold;
  color: var(--white);
  cursor: pointer;
}

.regist_btn:hover {
  outline: 2px solid var(--secondary-light);
}

.search-bar {
  display: flex;
  background-color: pink;
  border-radius: 10px;
  height: 38px;
}

.search-bar input {
  padding: 10px;
  height: 100%;
  border: 1px solid var(--gray-medium);
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}

.search-bar_button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 100%;
  cursor: pointer;
  border: 1px solid var(--gray-medium);
  border-left: none;
  background-color: var(--gray-medium);
  color: var(--white);
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}

.post {
  display: grid;
  grid-template-columns: 3fr 1fr 1fr;
  padding: 0px 16px;
  width: 100%;
  height: 40px;
  border-bottom: 1px solid var(--gray-medium);
  border-top: none;
  border-left: none;
  border-right: none;
  cursor: pointer;
  /* 수직 중앙 정렬 */
  align-items: center;
}

.first-post {
  border-top: 1px solid var(--gray-medium);
}

.post-title {
  font-size: 16px;
  color: var(--gray-dark);
}

.post-author {
  font-size: 14px;
  color: var(--gray-medium);
  margin-top: 4px;
}

.pagination {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.pagination_btn {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px 2px;
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 100%;
  background-color: var(--white);
  color: var(--gray-dark);
  cursor: pointer;
}

.light_btn {
  color: var(--gray-medium);
}

.active_btn {
  background-color: var(--secondary-light);
}
</style>
