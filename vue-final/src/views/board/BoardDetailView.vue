<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { ref, onMounted } from "vue";
import { RouterLink, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { localAuthAxios } from "@/utils/http-commons";
const localAuth = localAuthAxios();

// 스토어 가져오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const route = useRoute();
const postId = route.params.id;

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

// 모달 open
const modalOpen = ref(false);
const setModalOpen = () => {
  modalOpen.value = !modalOpen.value;
};

// dropdown 메뉴 open
const dropdownOpen = ref(false);
const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value;
};

// form 전송
const handleSubmit = async () => {
  console.log("submit 호출");
  console.log(post);
  try {
    const response = await localAuth.patch(`http://localhost:80/board`, post.value);
    console.log(response.data); // 서버로부터의 응답 확인
    setModalOpen();
  } catch (error) {
    console.error("Error submitting form data:", error);
    // 전송 중에 오류가 발생한 경우, 오류 메시지를 표시하거나 사용자에게 알림을 제공
  }
};

// 삭제 api
const handleDelete = async () => {
  console.log("delete 호출");
  try {
    const response = await axios.delete(`http://localhost:80/board/${postId}`);
    console.log(response.data); // 서버로부터의 응답 확인
    goBack();
  } catch (error) {
    console.error("Error submitting form data:", error);
    // 전송 중에 오류가 발생한 경우, 오류 메시지를 표시하거나 사용자에게 알림을 제공
  }
};

// mount 시에 postId에 해당하는 게시물 정보 가져오기
const post = ref({ boardId: "", title: "", content: "", answer: "", createdAt: "", userId: "" });
const getPost = async () => {
  try {
    const response = await localAuth.get(`http://localhost:80/board/${postId}`); // 서버에서 데이터 가져오기
    post.value = response.data; // 데이터를 변수에 저장
    console.log(response.data);
    console.log(post.value);
    if (userId.value === post.value.userId) isMine.value = true;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};
const isMine = ref(false);
const isHost = ref(false);
onMounted(() => {
  console.log("mounted!!!");
  console.log(userId.value);
  if (userId.value === "host") isHost.value = true;
  getPost();
});
</script>

<template>
  <MainHeader></MainHeader>
  <div class="container">
    <div class="wrapper">
      <h2 class="title">
        <button @click="goBack" type="button" class="history-btn">
          <font-awesome-icon :icon="['fas', 'angle-left']" />
        </button>
        문의 게시글
        <button v-if="isMine || isHost" @click="toggleDropdown" type="button" class="options-btn">
          <font-awesome-icon :icon="['fas', 'bars']" />
        </button>
        <div v-if="dropdownOpen" class="dropdown-menu">
          <button v-if="isMine" type="button">
            <RouterLink :to="{ name: 'boardModify', params: { id: postId } }"> 수정 </RouterLink>
          </button>
          <button v-if="isMine || isHost" @click="handleDelete" type="button">삭제</button>
        </div>
      </h2>
    </div>
    <div class="wrapper">
      <form class="form">
        <div class="form_group">
          <label class="form_label" for="title">제목</label>
          <div class="form_input">{{ post.title }}</div>
        </div>
        <div class="form_group">
          <label class="form_label" for="content">내용</label>
          <div class="form_textarea">{{ post.content }}</div>
        </div>
        <p class="answer" v-if="post.answer">{{ post.answer }}</p>
        <p class="answer" v-if="!post.answer">아직 답변이 없습니다.</p>
        <button v-if="isHost" @click="setModalOpen" class="submit_btn" type="button">
          답변하기
        </button>
        <div @click="setModalOpen" v-if="modalOpen" class="modal">
          <!-- 모달 내용 -->
          <div class="modal-content" @click.stop>
            <span class="close" @click="setModalOpen">&times;</span>
            <h2>답변 작성</h2>
            <!-- 답변 입력 폼 -->
            <textarea v-model="post.answer" placeholder="답변을 작성해주세요"></textarea>
            <button class="modal-btn" @click="handleSubmit" type="button">전송</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  min-height: calc(100vh - 80px - 200px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 60px 0px;
}

a {
  color: inherit;
  text-decoration: none;
}

a:visited {
  color: inherit;
}
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 400px;
  margin-bottom: 36px;
}

.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.history-btn,
.options-btn {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  color: var(--gray-dark);
  border: none;
  border-radius: 100%;
  background-color: var(--white);
  cursor: pointer;
}

.history-btn:hover,
.options-btn:hover {
  background-color: var(--secondary-light);
}

.history-btn {
  top: 0;
  left: 0;
}

.options-btn {
  top: 0;
  right: 0;
}

.form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.form_group {
  display: flex;
  flex-direction: column;
  margin-top: 8px;
}

.form_label {
  font-size: 16px;
  font-weight: bold;
}

.form_input {
  display: flex;
  align-items: center;
  margin-top: 12px;
  margin-bottom: 16px;
  padding: 8px;
  width: 400px;
  height: 40px;
  padding: 0px 8px;
  border: 1px solid #ccc;
}

.form_textarea {
  display: flex;
  justify-content: flex-start;
  margin-top: 12px;
  margin-bottom: 16px;
  align-items: fl;
  width: 400px;
  height: 200px;
  padding: 8px;
  border: 1px solid #ccc;
}

.answer {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 16px;
  width: 400px;
  height: 100px;
  padding: 8px;
  border: 2px dashed var(--secondary);
}

.submit_btn {
  background-color: var(--secondary);
  color: var(--white);

  border: none;
  border-radius: 10px;

  width: 400px;
  height: 40px;

  font-size: 16px;
  font-weight: bold;

  margin-top: 32px;

  cursor: pointer;
}

.submit_btn:hover {
  outline: 4px solid var(--secondary-light);
}

.delete_btn {
  background-color: var(--red-medium);
  color: var(--white);
  border: none;
  border-radius: 10px;
  width: 400px;
  height: 40px;
  font-size: 16px;
  font-weight: bold;
  margin-top: 12px;
  cursor: pointer;
}

.delete_btn:hover {
  outline: 4px solid var(--red-light);
}

/* Dropdown 메뉴 스타일 */
.dropdown-menu {
  position: absolute;
  top: 32px; /* 제목과 아이콘의 높이에 맞게 조절 */
  right: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  overflow: hidden;
}

.dropdown-menu button {
  width: 100%;
  height: 32px;
  border: none;
  color: var(--gray-dark);
  background-color: inherit;
  cursor: pointer;
}

.dropdown-menu button:hover {
  background-color: var(--secondary-light);
}
/* 모달 스타일 */
.modal {
  display: flex; /* Flexbox를 사용하여 세로 가운데 정렬 */
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: center; /* 수평 가운데 정렬 */
  background-color: rgba(0, 0, 0, 0.4); /* 배경에 투명한 검정색 레이어 */
}

/* 모달 내용 스타일 */
.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px; /* 최대 너비 설정 */
}

/* 닫기 버튼 스타일 */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.modal-btn {
  margin-top: 8px;
  cursor: pointer;
  width: 48px;
  height: 28px;
  background-color: var(--secondary);
  color: var(--white);
  border: none;
  border-radius: 10px;
}

.modal-btn:hover {
  outline: 2px solid var(--secondary-light);
}

/* 입력 폼 스타일 */
textarea {
  width: 100%;
  height: 100px; /* 원하는 높이 설정 */
  resize: vertical; /* 수직 크기 조절 허용 */
}
</style>
