<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { ref, reactive, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { localAuthAxios } from "@/utils/http-commons";
const localAuth = localAuthAxios();

// 스토어 가져오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const router = useRouter();
const route = useRoute();
const postId = route.params.id;

const goBack = () => {
  router.go(-1);
};

//form 전송
const formData = reactive({
  boardId: postId,
  title: "",
  content: "",
});
const formError = reactive({
  title: "",
  content: "",
});
const handleSubmit = async () => {
  console.log("submit 호출");
  const isValidate = validate();
  if (isValidate) {
    try {
      const response = await axios.patch(`http://localhost:80/board`, formData);
      console.log(response.data); // 서버로부터의 응답 확인
      if (response.data === "SUCCESS") {
        goBack();
      }
    } catch (error) {
      console.error("Error submitting form data:", error);
      // 전송 중에 오류가 발생한 경우, 오류 메시지를 표시하거나 사용자에게 알림을 제공
    }
  } else {
    alert("제목, 내용을 모두 입력해주세요");
  }
};
const validate = () => {
  console.log("formData: ", formData);
  let flag = true;
  if (!formData.title || formData.title === " ") {
    formError.title = "제목을 입력해주세요";
    flag = false;
  } else {
    formError.title = "";
  }
  if (!formData.content || formData.content === " ") {
    formError.content = "내용을 입력해주세요";
    flag = false;
  } else {
    formError.content = "";
  }
  return flag;
};

//mount시에 postId에 해당하는 게시물 정보가져오기
const post = ref({ title: "", content: "" });
const getPost = async () => {
  try {
    const response = await axios.get(`http://localhost:80/board/${postId}`); // 서버에서 데이터 가져오기
    post.value = response.data; // 데이터를 변수에 저장
    formData.title = response.data.title;
    formData.content = response.data.content;
    console.log(response.data);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};
onMounted(() => {
  console.log("mounted!!!");
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
        문의 게시글 수정
      </h2>
    </div>
    <div class="wrapper">
      <form class="form">
        <div class="form_group">
          <label class="form_label" for="title">제목</label>
          <input
            class="form_input"
            type="text"
            id="title"
            name="title"
            v-model="formData.title"
            required
          />
        </div>
        <div class="form_group">
          <label class="form_label" for="content">내용</label>
          <textarea
            class="form_textarea"
            type="text"
            id="content"
            name="content"
            v-model="formData.content"
            required
          ></textarea>
        </div>
        <button @click="handleSubmit" class="submit_btn" type="button">수정</button>
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

.wrapper {
  position: relative;
  width: 400px;
  margin-bottom: 36px;
}

.title {
  display: flex;
  justify-content: center;
  align-items: center;
}

.history-btn {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  left: 0px;
  width: 32px;
  height: 32px;
  color: var(--gray-dark);
  border: none;
  border-radius: 100%;
  background-color: var(--white);
  cursor: pointer;
}

.history-btn:hover {
  background-color: var(--secondary-light);
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

.form_input {
  width: 400px;
  height: 40px;
  padding: 0px 8px;
}

.form_textarea {
  width: 400px;
  height: 400px;
  padding: 8px;
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
  outline: 2px solid var(--secondary-light);
}
</style>
