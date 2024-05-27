<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { localFormAuthAxios } from "@/utils/http-commons";
import axios from "axios";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

// 스토어 가져오기
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const localFormAuth = localFormAuthAxios();

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

// form 전송
const formData = reactive({
  title: "",
  imagePath: "",
  intro: "",
  description: "",
  userId: userId.value,
});
const formError = reactive({
  title: "",
  imagePath: "",
  intro: "",
  description: "",
});
const imagePreview = ref(null); // 이미지 미리보기를 위한 변수

const handleSubmit = async () => {
  console.log("submit 호출");
  const isValidate = validate();
  if (isValidate) {
    try {
      // FormData 객체 생성
      const formDataToSend = new FormData();

      // JSON 데이터를 문자열로 변환 후 추가
      const jsonPayload = JSON.stringify({
        userId: formData.userId,
        title: formData.title,
        intro: formData.intro,
        description: formData.description,
      });
      formDataToSend.append("hotplace", new Blob([jsonPayload], { type: "application/json" }));

      // 이미지 파일 추가
      if (formData.imagePath) {
        formDataToSend.append("image", formData.imagePath);
      }

      const response = await localFormAuth.post("/hotplace", formDataToSend, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `${sessionStorage.getItem("accessToken")}`,
        },
      });

      console.log(response.data); // 서버로부터의 응답 확인
      //alert("등록되었습니다!");
      router.replace({ name: "hotplace" });
    } catch (error) {
      console.error("Error submitting form data:", error);
      // 전송 중에 오류가 발생한 경우, 오류 메시지를 표시하거나 사용자에게 알림을 제공
    }
  } else {
    alert("모든 값을 입력해주세요.");
  }
};

const validate = () => {
  console.log("===>validate FormData: ", formData);
  let flag = true;
  if (!formData.title || formData.title.trim() === "") {
    formError.title = "제목을 입력해주세요";
    flag = false;
  } else {
    formError.title = "";
  }

  if (!formData.imagePath) {
    formError.imagePath = "이미지를 등록해주세요";
    flag = false;
  } else {
    formError.imagePath = "";
  }
  if (!formData.intro || formData.intro.trim() === "") {
    formError.intro = "소개글을 입력해주세요";
    flag = false;
  } else {
    formError.intro = "";
  }
  if (!formData.description || formData.description.trim() === "") {
    formError.description = "내용을 입력해주세요";
    flag = false;
  } else {
    formError.description = "";
  }
  return flag;
};

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (!file) {
    formError.imagePath = "이미지를 등록해주세요";
    formData.imagePath = "";
    imagePreview.value = null;
  } else {
    formData.imagePath = file;
    formError.imagePath = "";
    imagePreview.value = URL.createObjectURL(file);
  }
};
</script>

<template>
  <MainHeader></MainHeader>
  <div class="container">
    <div class="wrapper">
      <h2 class="title">
        <button @click="goBack" type="button" class="history-btn">
          <font-awesome-icon :icon="['fas', 'angle-left']" />
        </button>
        핫플레이스 등록
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
          <span class="form_error">{{ formError.title }}</span>
        </div>
        <div class="form_group">
          <label class="form_label" for="imagePath">이미지</label>
          <input type="file" class="form_input" accept="image/*" @change="handleImageUpload" />
          <span class="form_error">{{ formError.imagePath }}</span>
        </div>
        <div class="form_group form_input_wrapper">
          <div class="form_image_preview" v-if="imagePreview">
            <img :src="imagePreview" alt="이미지 미리보기" />
          </div>
        </div>
        <div class="form_group">
          <label class="form_label" for="intro">소개글</label>
          <input
            class="form_input"
            type="text"
            id="intro"
            name="intro"
            v-model="formData.intro"
            required
          />
          <span class="form_error">{{ formError.intro }}</span>
        </div>
        <div class="form_group">
          <label class="form_label" for="description">내용</label>
          <textarea
            class="form_textarea"
            id="description"
            name="description"
            v-model="formData.description"
            required
          ></textarea>
          <span class="form_error">{{ formError.description }}</span>
        </div>
        <button @click="handleSubmit" class="submit_btn" type="button">등록</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 60px 0;
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
  left: 0;
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

.form_label {
  margin-bottom: 4px;
  font-size: 16px;
}

.form_input {
  width: 400px;
  height: 40px;
  padding: 0 8px;
}

.form_textarea {
  width: 400px;
  height: 200px;
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
  outline: 4px solid var(--secondary-light);
}

.form_image_preview {
  background-color: var(--gray-200);
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 4px;
}

.form_image_preview img {
  max-height: 100%;
  max-width: 100%;
  aspect-ratio: 4/3;
}

.form_error {
  color: red;
  font-size: 14px;
  margin-top: 4px;
}
</style>
