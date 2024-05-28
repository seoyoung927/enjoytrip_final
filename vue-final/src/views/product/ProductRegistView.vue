<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import FormField from "@/components/product/FormField.vue";
import ImageUpload from "@/components/product/ImageUpload.vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { localAuthAxios, localFormAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();
const localFormAuth = localFormAuthAxios();

//go back
const router = useRouter();
const goBack = () => {
  router.go(-1);
};

const formData = reactive({
  title: "",
  images: [],
  price: 0,
  people: 0,
  intro: "",
  content: "",
  warning: "",
});

const imagePreviews = ref([]);

const formError = reactive({
  title: "",
  images: "",
  price: "",
  people: "",
  intro: "",
  content: "",
  warning: "",
});

const updateFormField = (field, value) => {
  formData[field] = value;
};

const handleImageUpload = (files) => {
  if (files.length > 4) {
    formError.images = "최대 4장까지 업로드 가능합니다";
  } else {
    formData.images = files;
    formError.images = "";
    imagePreviews.value = files.map((file) => URL.createObjectURL(file));
  }
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
    formError.warning = "";
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

const handleSubmit = async () => {
  if (!validate()) {
    alert("모든 값을 입력해주세요.");
    return;
  }

  const formDataToSend = new FormData();
  for (const key in formData) {
    if (key !== "images") {
      formDataToSend.append(key, formData[key]);
    }
  }

  try {
    //await 키워드를 사용하면 Promise가 완료될 때까지 기다릴 수 있어, 비동기 코드를 동기적으로 작성할 수 있습니다.
    const response = await localAuth.post("/product", formDataToSend); 
    console.log("===> response: ", response.data);
    
    const imgFormDataToSend = new FormData();
    imgFormDataToSend.append("pid", response.data);
    formData.images.forEach((image) => {
      imgFormDataToSend.append("files", image);
    });

    const response2 = await localFormAuth.post("/product/file", imgFormDataToSend);
    console.log("===> response2: ", response2.data);

    //alert("등록되었습니다!");
    router.go(-1);
  } catch (error) {
    console.error("Error submitting form data:", error);
  }
};
</script>

<template>
  <MainHeader />
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
      <form @submit.prevent="handleSubmit" class="form">
        <FormField
          label="제목"
          :value="formData.title"
          :error="formError.title"
          @input="updateFormField('title', $event)"
          required
        />
        <ImageUpload
          :files="formData.images"
          :error="formError.images"
          @update:files="handleImageUpload($event)"
          :previews="imagePreviews"
        />
        <FormField
          label="가격"
          type="number"
          :value="formData.price"
          :error="formError.price"
          @input="updateFormField('price', $event)"
          required
        />
        <FormField
          label="인원수"
          type="number"
          :value="formData.people"
          :error="formError.people"
          @input="updateFormField('people', $event)"
          required
        />
        <FormField
          label="소개글"
          :value="formData.intro"
          :error="formError.intro"
          @input="updateFormField('intro', $event)"
          required
        />
        <FormField
          label="내용"
          type="textarea"
          :value="formData.content"
          :error="formError.content"
          @input="updateFormField('content', $event)"
          required
        />
        <FormField
          label="주의사항"
          type="textarea"
          :value="formData.warning"
          :error="formError.warning"
          @input="updateFormField('warning', $event)"
          required
        />
        <button type="submit" class="submit_btn">등록</button>
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
