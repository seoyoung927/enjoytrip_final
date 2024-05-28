<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import CardSlider from "@/components/common/CardSlider.vue";
import { ref, onMounted } from "vue";
import { localAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();

const cards = ref([]);
const places = ref([]);

//mount시에 productId에 해당하는 게시물 정보가져오기
const getPlans = async () => {
  try {
    const response = await localAuth.get(`/plan`); // 서버에서 데이터 가져오기
    places.value = response.data;
    console.log(response.data);
    console.log("===>getPlans: ", response.data);
  } catch (error) {
    console.error("Error getPlans data:", error);
  }
};
//mount시에 myBook 정보가져오기(예약)
const getBooks = async () => {
  try {
    const response = await localAuth.get(`/product/myBook`); // 서버에서 데이터 가져오기
    cards.value = response.data;
    console.log(response.data);
    console.log("===>getBooks: ", response.data);
  } catch (error) {
    console.error("Error getBooks data:", error);
  }
};
onMounted(() => {
  console.log("mounted!!!");
  getBooks();
  getPlans();
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <div class="wrapper">
      <h2 class="card-slider-title">🎫 예약한 상품</h2>
      <CardSlider :cards="cards"></CardSlider>
    </div>
    <div class="wrapper">
      <h2 class="card-slider-title">🧳 찜한 장소</h2>
      <CardSlider :cardType="'place'" :cards="places"></CardSlider>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 60px 60px; /* Adjust padding as needed */
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 80px - 200px);
}

.wrapper {
  max-width: 1024px; /* Ensure wrapper doesn't exceed a certain width */
  width: 100%;
  max-width: 1024px; /* Ensure wrapper doesn't exceed a certain width */
  padding: 20px 0px; /* Adjust padding as needed */
}

.card-slider-title {
  width: 100%;
  margin-bottom: 16px;
}

/* 768px 이상에서*/
@media (min-width: 768px) {
  .container {
    padding: 60px 120px;
  }

  .wrapper {
    padding: 60px 20px; /* Adjust padding as needed */
  }
}

/* 992px 이상에서*/
@media (min-width: 992px) {
}

/* 1200px 이상에서*/
@media (min-width: 1200px) {
}
</style>
