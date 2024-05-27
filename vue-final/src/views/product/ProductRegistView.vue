<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { localAuthAxios, localFormAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();
const localFormAuth = localFormAuthAxios();
const router = useRouter();

const formData = reactive({
  title: "",
  images: [],
  price: 0,
  people: 0,
  intro: "",
  content: "",
  warning: "",
});

const formError = reactive({
  title: "",
  images: "",
  price: "",
  people: "",
  intro: "",
  content: "",
  warning: "",
});

const imagePreviews = ref([]);

const goBack = () => {
  router.go(-1);
};

const validate = () => {
  let isValid = true;

  if (!formData.title.trim()) {
    formError.title = "제목을 입력해주세요";
    isValid = false;
  } else {
    formError.title = "";
  }

  if (formData.images.length !== 4) {
    formError.images = "이미지를 4장 업로드해주세요";
    isValid = false;
  } else {
    formError.images = "";
  }

  if (!formData.intro.trim()) {
    formError.intro = "소개글을 입력해주세요";
    isValid = false;
  } else {
    formError.intro = "";
  }

  if (!formData.content.trim()) {
    formError.content = "내용을 입력해주세요";
    isValid = false;
  } else {
    formError.content = "";
  }

  if (!formData.warning.trim()) {
    formError.warning = "주의사항을 입력해주세요";
    isValid = false;
  } else {
    formError.warnning = "";
  }

  if (formData.price < 0 || formData.price > 1000000) {
    formError.price = "가격은 0원 이상 100만원 이하이어야 합니다";
    isValid = false;
  } else {
    formError.price = "";
  }

  if (formData.people < 0 || formData.people > 50) {
    formError.people = "인원수는 0명 이상 50명 이하이어야 합니다";
    isValid = false;
  } else {
    formError.people = "";
  }

  return isValid;
};

const handleImageUpload = (event) => {
  const files = Array.from(event.target.files);

  if (files.length > 4) {
    formError.images = "최대 4장까지 업로드 가능합니다";
  } else {
    formData.images = files;
    formError.images = "";
    imagePreviews.value = files.map((file) => URL.createObjectURL(file));
  }
};

const handleSubmit = async () => {
  if (!validate()) {
    alert("모든 값을 입력해주세요.");
    return;
  }

  try {
    const formDataToSend = createFormData(formData);
    console.log("===> formData: ", formData);
    await localAuth.post("/product", formDataToSend).then(async (res) => {
      console.log("===> response: ", res.data);
      // imgFormData 처리
      const imgFormDataToSend = new FormData();
      imgFormDataToSend.append("pid", res.data);

      // 각각의 파일을 FormData에 개별적으로 추가
      formData.images.forEach((image, index) => {
        imgFormDataToSend.append("files", image);
      });

      const response2 = await localFormAuth.post("/product/file", imgFormDataToSend);
      console.log("===> response2: ", response2.data);
      /////////////////////////////////////
    });
    alert("등록되었습니다!");
    router.go(-1);
  } catch (error) {
    console.error("Error submitting form data:", error);
  }
};

const createFormData = (data) => {
  const formDataToSend = new FormData();

  formDataToSend.append("title", data.title);
  formDataToSend.append("intro", data.intro);
  formDataToSend.append("content", data.content);
  formDataToSend.append("warning", data.warning);
  formDataToSend.append("price", data.price);
  formDataToSend.append("people", data.people);
  //formDataToSend.append("images", data.images);
  // data.images.forEach((image, index) => {
  //   formDataToSend.append(`img${index + 1}`, image);
  // });

  return formDataToSend;
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
        상품 등록
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
          <label class="form_label" for="images">이미지</label>
          <input
            type="file"
            class="form_input"
            accept="image/*"
            multiple
            @change="handleImageUpload"
          />
          <span class="form_error">{{ formError.images }}</span>
        </div>
        <div class="form_group form_input_wrapper">
          <div class="form_image_preview" v-for="(image, index) in imagePreviews" :key="index">
            <img :src="image" alt="이미지 미리보기" />
          </div>
        </div>
        <div class="form_group">
          <label class="form_label" for="price">가격</label>
          <input
            class="form_input"
            type="number"
            id="price"
            name="price"
            v-model="formData.price"
            required
          />
          <span class="form_error">{{ formError.price }}</span>
        </div>
        <div class="form_group">
          <label class="form_label" for="people">인원수</label>
          <input
            class="form_input"
            type="number"
            id="people"
            name="people"
            v-model="formData.people"
            required
          />
          <span class="form_error">{{ formError.people }}</span>
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
          <label class="form_label" for="content">내용</label>
          <textarea
            class="form_textarea"
            id="content"
            name="content"
            v-model="formData.content"
            required
          ></textarea>
          <span class="form_error">{{ formError.content }}</span>
        </div>
        <div class="form_group">
          <label class="form_label" for="warning">주의사항</label>
          <textarea
            class="form_textarea"
            id="warning"
            name="warning"
            v-model="formData.warning"
            required
          ></textarea>
          <span class="form_error">{{ formError.warning }}</span>
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
</style>
