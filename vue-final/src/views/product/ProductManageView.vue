<script setup>
import { ref, onMounted } from "vue";
import { RouterLink } from "vue-router";
import { localAuthAxios } from "@/utils/http-commons";
const localAuth = localAuthAxios();
import MainHeader from "@/components/common/MainHeader.vue";
import CardSlider from "@/components/common/CardSlider.vue";

const cards = ref([]);
const getMyProducts = async () => {
  try {
    const response = await localAuth.get(`/product/myProduct`); // 서버에서 데이터 가져오기
    cards.value = response.data;
    console.log(response.data);
    console.log("===>getMyProducts: ", response.data);
  } catch (error) {
    console.error("Error getMyProducts data:", error);
  }
};
onMounted(() => {
  console.log("mounted!!!");
  getMyProducts();
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <div class="wrapper">
      <h2 class="card-slider-title">🔖 나의 상품</h2>
      <div class="group">
        <RouterLink :to="{ name: 'productRegist' }">
          <button type="button" class="btn-product-regist">상품 등록하기</button>
        </RouterLink>
      </div>
      <CardSlider :cardType="'myProduct'" :cards="cards"></CardSlider>
    </div>
  </div>
</template>

<style scoped>
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
  width: 50%;
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
  margin-bottom: 24px;
}

.btn-product-regist {
  width: 128px;
  height: 40px;
  background-color: var(--secondary);
  color: var(--white);
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.btn-product-regist:hover {
  outline: 4px solid var(--secondary-light);
}

/* 768px 이상에서 카드 2개 표시 */
@media (min-width: 768px) {
  .wrapper {
    width: 100%;
  }

  .container {
    padding: 60px 120px; /* Adjust padding as needed */
  }

  .wrapper {
    padding: 60px 20px; /* Adjust padding as needed */
  }
}

/* 992px 이상에서 카드 3개 표시 */
@media (min-width: 992px) {
}

/* 1200px 이상에서 카드 4개 표시 */
@media (min-width: 1200px) {
}
</style>
