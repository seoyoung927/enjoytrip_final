<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import CardSlider from "@/components/common/CardSlider.vue";
import { ref, onMounted } from "vue";
import { localAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();

// import imgSeoul from "@/assets/img/Seoul.jpg";
// import imgDaejeon from "@/assets/img/Daejeon.jpg";
// import imgGangneung from "@/assets/img/Gangneung.jpg";
// import imgJeonju from "@/assets/img/Jeonju.jpg";
// import imgBusan from "@/assets/img/Busan.jpg";
// import imgGyeongju from "@/assets/img/Gyeongju.jpg";
// import imgJeju from "@/assets/img/Jeju.jpg";
// import imgYeosu from "@/assets/img/Yeosu.jpg";
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
const cards = ref([]);

//mount시에 productId에 해당하는 게시물 정보가져오기
const getProducts = async () => {
  try {
    const response = await localAuth.get(`/product/search`); // 서버에서 데이터 가져오기
    cards.value = response.data;
    console.log("===>getProducts: ", response.data);
  } catch (error) {
    console.error("Error getProducts data:", error);
  }
};
onMounted(() => {
  console.log("mounted!!!");
  getProducts();
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <section class="wrapper">
      <h2 class="card-slider-title">🚠 최근에 추가된 상품</h2>
      <CardSlider :cards="cards"></CardSlider>
    </section>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  margin: auto;
  padding: 60px 60px; /* Adjust padding as needed */
  min-height: calc(100vh - 80px - 200px);
}

.wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
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
    padding: 60px 120px; /* Adjust padding as needed */
  }

  .wrapper {
    padding: 60px 20px; /* Adjust padding as needed */
  }
}
</style>
