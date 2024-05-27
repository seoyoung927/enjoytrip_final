<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import CardSlider from "@/components/product/CardSlider.vue";
import { ref, onMounted } from "vue";
import { localAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();

const cards = ref([]);
const places = ref([]);

import imgSeoul from "@/assets/img/Seoul.jpg";
import imgDaejeon from "@/assets/img/Daejeon.jpg";
import imgGangneung from "@/assets/img/Gangneung.jpg";
import imgJeonju from "@/assets/img/Jeonju.jpg";
import imgBusan from "@/assets/img/Busan.jpg";
import imgGyeongju from "@/assets/img/Gyeongju.jpg";
import imgJeju from "@/assets/img/Jeju.jpg";
import imgYeosu from "@/assets/img/Yeosu.jpg";

// const cards = [
//   { id: 1, src: imgSeoul, title: "서울", intro: "밤낮없이 활기찬 글로벌 도시" },
//   { id: 2, src: imgDaejeon, title: "대전", intro: "과학과 문화의 도시" },
//   { id: 3, src: imgGangneung, title: "강릉", intro: "바다향기 가득한 여행지" },
//   { id: 4, src: imgJeonju, title: "전주", intro: "전통과 현대의 조화로움" },
//   { id: 5, src: imgBusan, title: "부산", intro: "매력적인 해안가와 다양한 맛집이 어울러진 도시" },
//   { id: 6, src: imgGyeongju, title: "경주", intro: "역사의 숨결이 가득한 도시" },
//   { id: 7, src: imgJeju, title: "제주", intro: "자연 속의 평화와 아름다움" },
//   { id: 8, src: imgYeosu, title: "여수", intro: "로맨틱한 일몰이 인상적인 도시" },
// ];

// const places = [
//   { id: 1, src: imgSeoul, title: "서울스카이", intro: "밤낮없이 활기찬 글로벌 도시" },
//   { id: 2, src: imgDaejeon, title: "대전", intro: "과학과 문화의 도시" },
//   { id: 3, src: imgGangneung, title: "강릉", intro: "바다향기 가득한 여행지" },
//   { id: 4, src: imgJeonju, title: "전주", intro: "전통과 현대의 조화로움" },
//   { id: 5, src: imgBusan, title: "부산", intro: "매력적인 해안가와 다양한 맛집이 어울러진 도시" },
//   { id: 6, src: imgGyeongju, title: "경주", intro: "역사의 숨결이 가득한 도시" },
//   { id: 7, src: imgJeju, title: "제주", intro: "자연 속의 평화와 아름다움" },
//   { id: 8, src: imgYeosu, title: "여수", intro: "로맨틱한 일몰이 인상적인 도시" },
// ];

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
//mount시에 productId에 해당하는 게시물 정보가져오기
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
