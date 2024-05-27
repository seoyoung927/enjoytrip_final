<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { reactive, ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { localAuthAxios } from "@/utils/http-commons";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

// 스토어 가져오기
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const localAuth = localAuthAxios();
const route = useRoute();
const productId = route.params.id;

import imgSeoul1 from "@/assets/img/seoul/seoul1.jpg";
import imgSeoul2 from "@/assets/img/seoul/seoul2.jpg";
import imgSeoul3 from "@/assets/img/seoul/seoul3.jpg";
import imgSeoul4 from "@/assets/img/seoul/seoul4.jpg";

const card = ref({
  booked: "",
  content: "", 
  img1: "",
  img2: "",
  img3: "",
  img4: "",
  intro: "intro",
  memo: "", 
  ownerId: "",
  people: "",
  pid: "",
  price: "", 
  title: "",
  warning  :""
})

const state = reactive({
  memo: "",
  isReserved: false,
  isEditing: false,
});

const newMemo = ref("");

const startEditing = () => {
  state.isEditing = true;
  newMemo.value = state.memo;
};

const saveMemo = async () => {
  try {
    card.value.memo = newMemo.value;
    const formDataToSend = new FormData();
    formDataToSend.append("pid", card.value.pid);
    formDataToSend.append("memo", newMemo.value);
    formDataToSend.append("userId", userId.value);
    const response = await localAuth.patch("product/book", formDataToSend);
    console.log("===> saveMemo:", response.data);
    //alert("등록되었습니다!");
  } catch (error) {
    console.error("Error submitting form data:", error);
  }
  state.memo = newMemo.value;
  state.isEditing = false;
};

const deleteMemo = async() => {
  try {
    card.value.memo = "";
    const formDataToSend = new FormData();
    formDataToSend.append("pid", card.value.pid);
    formDataToSend.append("memo", "");
    formDataToSend.append("userId", userId.value);
    const response = await localAuth.patch("product/book", formDataToSend);
    console.log("==> deleteMemo: ", response.data);
    //alert("삭제되었습니다!");
  } catch (error) {
    console.error("Error submitting form data:", error);
  }
  state.memo = "";
  state.isEditing = false;
};

//상품 예약
const onReserve = async () => {
  try {
    card.value.booked = true;
    const response = await localAuth.post(`/product/book/${productId}`);
    console.log("===> onReserve: ", response.data);
  } catch (error) {
    console.error("Error 상품 예약 중 오류 발생:", error);
  }
  state.isReserved = true;
};

const onCancelReserve = async () => {
  try {
    card.value.booked = false;
    card.value.memo = "";

    const response = await localAuth.delete(`/product/book/${productId}`);
    console.log("===> onCancelReserve: ", response.data);
  } catch (error) {
    console.error("Error 상품 예약 취소 중 오류 발생:", error);
  }
  newMemo.value = "";
  state.memo = "";
  state.isReserved = false;
};

// Mount시에 productId에 해당하는 게시물 정보가져오기
const getProduct = async () => {
  try {
    const response = await localAuth.get(`/product/search/${productId}`);
    console.log("getProduct: ", response.data);
    card.value = response.data;
    card.value.img1 = response.data.images[0].imagePath;
    card.value.img2 = response.data.images[1].imagePath;
    card.value.img3 = response.data.images[2].imagePath;
    card.value.img4 = response.data.images[3].imagePath;
    state.isReserved = card.value.booked;
    state.memo = card.value.memo;
    newMemo.value = card.value.memo;
    console.log("card: ", card);
  } catch (error) {
    console.error("Error getProduct data:", error);
  }
};

const auth = ref(false);
onMounted(() => {
  console.log("mounted!!!");
  console.log(userId?.value === null);
  auth.value = !(userId?.value === null);
  getProduct();
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <div class="detail-wrapper">
      <div class="detail-imgs">
        <div class="big-img">
          <img :src="`http://localhost:80${card.img1}`" alt="큰 이미지" />
        </div>
        <div class="small-imgs">
          <div class="small-img">
            <img :src="`http://localhost:80${card.img2}`" alt="작은 이미지1" />
          </div>
          <div class="small-img">
            <img :src="`http://localhost:80${card.img3}`" alt="작은 이미지2" />
          </div>
          <div class="small-img">
            <img :src="`http://localhost:80${card.img4}`" alt="작은 이미지3" />
          </div>
        </div>
      </div>
      <div class="detail-contents">
        <h3 class="detail-title">
          {{ card.title }}
        </h3>
        <p class="detail-content">
          <pre>{{ card.content }}</pre>
        </p>
        <h3 class="detail-warning-title">❗ 주의사항</h3>
        <p class="detail-warning">
          <pre>{{ card.warning }}</pre>
        </p>
      </div>
    </div>
    <div v-if="auth" class="reserve-wrapper">
      <div class="reserve-btn-wrapper">
        <p v-if="!state.isReserved" class="reserve-btn-text">예약 후 즉시 확정 됩니다.</p>
        <button v-if="!state.isReserved" @click="onReserve" type="button" class="reserve-btn">예약하기</button>
        <button v-else @click="onCancelReserve" type="button" class="reserve-btn">예약 취소하기</button>
      </div>
      <div class="memo-wrapper" v-if="state.isReserved">
        <template v-if="card.memo">
          <p>{{ card.memo }}</p>
          <button class="successMemo-btn" type="button" @click="startEditing">수정</button>
          <button class="cancelMemo-btn" type="button" @click="deleteMemo">삭제</button>
        </template>
        <template v-else>
          <p class="memo-text">아직 메모가 없습니다.</p>
          <button class="addMemo-btn" type="button" @click="startEditing">메모 추가하기</button>
        </template>
        <div v-if="state.isEditing">
          <textarea
            class="memo-textarea"
            v-model="newMemo"
            placeholder="메모를 입력하세요"
          ></textarea>
          <button class="successMemo-btn" type="button" @click="saveMemo">저장</button>
          <button class="cancelMemo-btn" type="button" @click="state.isEditing = false">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 60px 60px;
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 160px);
}

.detail-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
}

.detail-imgs {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.big-img {
  width: 100%;
  aspect-ratio: 4 / 3;
}

.small-imgs {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 4px;
  width: 100%;
  gap: 4px;
}

.small-img {
  width: calc((100% - 12px) / 3);
  aspect-ratio: 4 / 3;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  object-position: center;
}

.big-img img,
.small-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-contents {
  width: 100%;
}

.detail-title {
  margin-top: 64px;
  font-size: 22px;
  line-height: 1.8;
}

.detail-content {
  width: 100%;
  margin-top: 16px;
  font-size: 16px;
  line-height: 1.8;
}

.detail-content pre {
  white-space: pre-wrap;
}

.detail-warning-title {
  margin-top: 64px;
  font-size: 22px;
  line-height: 1.8;
}

.detail-warning {
  margin-top: 16px;
  font-size: 16px;
  line-height: 1.8;
  white-space: pre-wrap;
}

.detail-warning pre {
  white-space: pre-wrap;
}

.reserve-wrapper {
  width: 100%;
  margin-top: 24px;
}

.reserve-btn-wrapper {
  width: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(0, 0, 0, 0.1);
  padding: 30px 40px;
}

.reserve-btn-text {
  color: var(--gray-medium);
  margin-bottom: 24px;
}

.reserve-btn {
  width: 100%;
  height: 48px;
  border: none;
  border-radius: 10px;
  background-color: var(--secondary);
  color: var(--white);
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
}

.reserve-btn:hover {
  outline: 4px solid var(--secondary-light);
}

.memo-wrapper {
  width: 100%;
  margin-top: 24px;
  padding: 30px 40px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.memo-text {
  color: var(--gray-medium);
  margin-bottom: 24px;
}

.memo-textarea {
  margin-top: 12px;
  width: 100%;
  height: 100px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.addMemo-btn {
  width: 100%;
  height: 48px;
  border: none;
  border-radius: 10px;
  background-color: var(--secondary);
  color: var(--white);
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
}

.addMemo-btn:hover {
  outline: 4px solid var(--secondary-light);
}

.successMemo-btn {
  width: 60px;
  height: 36px;
  background-color: var(--secondary);
  color: var(--white);
  margin-top: 12px;
  margin-right: 8px;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.successMemo-btn:hover {
  outline: 4px solid var(--secondary-light);
}

.cancelMemo-btn {
  width: 60px;
  height: 36px;
  background-color: var(--gray-medium);
  color: var(--white);
  margin-top: 12px;
  margin-right: 12px;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.cancelMemo-btn:hover {
  outline: 4px solid var(--gray-light);
}

/* 768px 이상에서 */
@media (min-width: 768px) {
  .container {
    padding: 60px 120px;
  }
}

/* 992px 이상에서 */
@media (min-width: 992px) {
}

/* 1200px 이상에서 */
@media (min-width: 1200px) {
  .container {
    padding: 60px 120px;
    flex-direction: row;
    align-items: flex-start;
  }

  .reserve-wrapper {
    margin-top: 0px;
  }

  .detail-wrapper {
    max-width: 640px;
  }

  .reserve-wrapper {
    max-width: 300px;
    margin-left: 40px;
  }

  .detail-contents,
  .detail-imgs {
    width: 640px;
  }
}
</style>
