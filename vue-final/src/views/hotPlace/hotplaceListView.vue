<script setup>
import { ref, onMounted, computed } from "vue";
import { RouterLink } from "vue-router";
import MainHeader from "@/components/common/MainHeader.vue";
import { localAuthAxios } from "@/utils/http-commons";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

// 스토어 가져오기
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const localAuth = localAuthAxios();

const cards = ref([]);

const currentPage = ref(1);
const itemsPerPage = 9;
const pagesPerGroup = 10; // 페이지 그룹당 페이지 수

const filterOption = ref("all");

const filteredCards = computed(() => {
  if (filterOption.value === "all") {
    return cards.value;
  }
  return cards.value.filter((card) => card.userId === userId.value);
});

const paginatedCards = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredCards.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(filteredCards.value.length / itemsPerPage);
});

const currentGroup = computed(() => {
  return Math.ceil(currentPage.value / pagesPerGroup);
});

const paginatedPages = computed(() => {
  const start = (currentGroup.value - 1) * pagesPerGroup + 1;
  const end = Math.min(start + pagesPerGroup - 1, totalPages.value);
  const pages = [];
  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

const changePage = (page) => {
  currentPage.value = page;
};

const prevPageGroup = () => {
  if (currentGroup.value > 1) {
    currentPage.value = (currentGroup.value - 2) * pagesPerGroup + 1;
  }
};

const nextPageGroup = () => {
  if (currentGroup.value * pagesPerGroup < totalPages.value) {
    currentPage.value = currentGroup.value * pagesPerGroup + 1;
  }
};

const changeFilter = (option) => {
  filterOption.value = option;
  currentPage.value = 1; // 필터를 변경할 때 첫 페이지로 이동
};

//mount시에 productId에 해당하는 게시물 정보가져오기
const getHotplaces = async () => {
  try {
    const response = await localAuth.get(`/hotplace`); // 서버에서 데이터 가져오기
    cards.value = response.data;
    console.log("===>getHotplaces: ", response.data);
    console.log("===>cards: ", cards.value);
  } catch (error) {
    console.error("Error getHotplaces data:", error);
  }
};

const auth = ref(false);
onMounted(() => {
  console.log("mounted!!!!!");
  auth.value = !(userId?.value === null);
  getHotplaces();
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <section class="wrapper">
      <h2 class="card-slider-title">🔥 핫플레이스</h2>
      <div class="filter-options">
        <button
          class="filter-option"
          @click="changeFilter('all')"
          :class="{ active: filterOption === 'all' }"
        >
          모든 핫플레이스
        </button>
        <button
          v-if="auth"
          class="filter-option"
          @click="changeFilter('mine')"
          :class="{ active: filterOption === 'mine' }"
        >
          내가 작성한 핫플레이스
        </button>
      </div>
      <div class="group">
        <RouterLink :to="{ name: 'hotplaceRegist' }">
          <button v-if="auth" type="button" class="btn-hotplace-regist">핫플레이스 등록하기</button>
        </RouterLink>
      </div>
      <div class="card-list">
        <div v-for="card in paginatedCards" :key="card.hid" class="card">
          <RouterLink
            v-if="card.userId === userId"
            :to="{
              name: 'hotplaceMyDetail',
              params: { id: card.hid },
            }"
          >
            <img
              :src="`http://localhost:80/${card.imagePath}`"
              :alt="card.title"
              class="card-image"
            />
            <h3 class="card-title">{{ card.title }}</h3>
            <p class="card-intro">{{ card.intro }}</p>
          </RouterLink>
          <RouterLink
            v-else
            :to="{
              name: 'hotplaceDetail',
              params: { id: card.hid },
            }"
          >
            <img
              :src="`http://localhost:80/${card.imagePath}`"
              :alt="card.title"
              class="card-image"
            />
            <h3 class="card-title">{{ card.title }}</h3>
            <p class="card-intro">{{ card.intro }}</p>
          </RouterLink>
        </div>
      </div>
      <div class="pagination">
        <button @click="prevPageGroup" :disabled="currentGroup === 1">이전</button>
        <button
          v-for="page in paginatedPages"
          :key="page"
          @click="changePage(page)"
          :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
        <button @click="nextPageGroup" :disabled="currentGroup * pagesPerGroup >= totalPages">
          다음
        </button>
      </div>
    </section>
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
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 60px 60px;
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 80px - 200px);
}

.wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1024px; /* Ensure wrapper doesn't exceed a certain width */
  padding: 20px 0px; /* Adjust padding as needed */
}

.card-slider-title {
  width: 100%;
  margin-bottom: 16px;
}

.group {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 32px;
  width: 100%;
}

.filter-options {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-bottom: 24px;
}

.filter-options button {
  padding: 8px 16px;
  border: none;
  background-color: var(--secondary);
  color: var(--white);
  border-radius: 4px;
  cursor: pointer;
}

.filter-options button.active {
  background-color: var(--primary);
}

.btn-hotplace-regist {
  width: 160px;
  height: 40px;
  background-color: var(--secondary);
  color: var(--white);
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.btn-hotplace-regist:hover {
  outline: 4px solid var(--secondary-light);
}

.card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  width: 100%;
  justify-content: center;
}

.card {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 60%;
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card-image {
  width: 100%;
  height: 200px; /* Set a fixed height for all images */
  aspect-ratio: 1/1;
  object-fit: cover; /* Ensure images cover the area without distortion */
  border-radius: 4px;
  margin-bottom: 8px;
}

.card-title {
  font-size: 1.25rem;
  margin-bottom: 8px;
}

.card-intro {
  font-size: 1rem;
  color: #666;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 80px;
  margin-bottom: 24px;
}

.pagination button {
  padding: 8px 16px;
  border: none;
  background-color: var(--secondary);
  color: var(--white);
  border-radius: 4px;
  cursor: pointer;
}

.pagination button.active {
  background-color: var(--primary);
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* 768px 이상에서*/
@media (min-width: 768px) {
  .container {
    padding: 60px 120px; /* Adjust padding as needed */
  }

  .wrapper {
    padding: 60px 20px; /* Adjust padding as needed */
  }

  .card {
    width: calc(50% - 16px); /* 2 cards per row */
  }
}

/* 1200px 이상에서 카드 4개 표시 */
@media (min-width: 992px) {
  .card {
    width: calc((100% - 32px) / 3); /* 3 cards per row */
  }
}
</style>
