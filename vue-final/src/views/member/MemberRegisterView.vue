<script setup>
import MainHeader from "@/components/common/MainHeader.vue";

import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { localAxios } from "@/utils/http-commons";

const local = localAxios();

const router = useRouter();

const options = ref([
  { id: "1", name: "서울" },
  { id: "2", name: "인천" },
  { id: "3", name: "대전" },
  { id: "4", name: "대구" },
  { id: "5", name: "광주" },
  { id: "6", name: "부산" },
  { id: "7", name: "울산" },
  { id: "8", name: "세종특별자치시" },
  { id: "31", name: "경기도" },
  { id: "32", name: "강원도" },
  { id: "33", name: "충청북도" },
  { id: "34", name: "충청남도" },
  { id: "35", name: "경상북도" },
  { id: "36", name: "경상남도" },
  { id: "37", name: "전라북도" },
  { id: "38", name: "전라남도" },
  { id: "39", name: "제주도" },
]);
const showOption = ref(false);
const setShowOption = () => {
  showOption.value = !showOption.value;
};
const setSelectedRegion = (regionName) => {
  formData.region = regionName;
};
const memberOptions = ref([
  { id: "1", name: "일반" },
  { id: "2", name: "호스트" },
]);
const showMemberOption = ref(false);
const setShowMemberOption = () => {
  showMemberOption.value = !showMemberOption.value;
};
const setSelectedMemberType = (memberType) => {
  formData.isHost = memberType;
};

const formData = reactive({
  email: "",
  password: "",
  name: "",
  userId: "",
  region: "지역선택",
  isHost: "회원선택",
});
const passwordCheck = ref("");
const formError = reactive({
  email: "",
  password: "",
  passwordCheck: "",
  name: "",
  userId: "",
  region: "",
  isHost: "",
});
const submitForm = () => {
  console.log(formData);
  const isValidate = validate();
  if (isValidate) {
    signup();
    router.replace({ name: "memberLogin" });
  }
};
const validate = () => {
  let flag = true;
  if (!formData.email || formData.email.trim() === "") {
    formError.email = "이메일을 입력해주세요";
    flag = false;
  } else {
    formError.email = "";
  }
  if (!formData.password) {
    formError.password = "비밀번호를 입력해주세요";
    flag = false;
  } else {
    formError.password = "";
  }
  if (!passwordCheck.value || passwordCheck.value.trim() === "") {
    formError.passwordCheck = "비밀번호를 확인해주세요.";
    flag = false;
  } else if (formData.password !== passwordCheck.value) {
    formError.passwordCheck = "비밀번호가 일치하지 않습니다";
    flag = false;
  } else {
    formError.passwordCheck = "";
  }
  if (!formData.name) {
    formError.name = "이름을 입력해주세요";
    flag = false;
  } else {
    formError.name = "";
  }
  if (!formData.userId) {
    formError.userId = "아이디를 입력해주세요";
    flag = false;
  } else {
    formError.userId = "";
  }
  if (!formData.region || formData.region === "지역선택") {
    formError.region = "지역을 선택해주세요";
    flag = false;
  } else {
    formError.region = "";
  }
  if (!formData.isHost || formData.isHost === "회원선택") {
    formError.isHost = "회원 타입을 선택해주세요";
    flag = false;
  } else {
    formError.isHost = "";
  }
  return flag;
};
//회원가입 api
const signup = async () => {
  formData.isHost = formData.isHost === "호스트";
  await local
    .post(`/member/signup`, formData)
    .then((res) => {
      console.log(res);
      //alert("회원가입이 완료되었습니다!");
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<template>
  <MainHeader></MainHeader>
  <main>
    <div class="container">
      <form class="form">
        <h2 class="form_title">회원가입</h2>
        <div class="form_group">
          <label class="form_label" for="text">이메일</label>
          <input
            class="form_input"
            type="email"
            id="email"
            name="email"
            v-model="formData.email"
            required
          />
          <p class="form_danger_text">{{ formError.email }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="nickname">아이디</label>
          <input
            class="form_input"
            type="text"
            id="userId"
            name="userId"
            v-model="formData.userId"
            required
          />
          <p class="form_danger_text">{{ formError.userId }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="password">비밀번호</label>
          <input
            class="form_input"
            type="password"
            id="password"
            name="password"
            v-model="formData.password"
            required
          />
          <p class="form_danger_text">{{ formError.password }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="password_check">비밀번호 확인</label>
          <input
            class="form_input"
            type="password"
            id="password"
            name="passwordCheck"
            v-model="passwordCheck"
            required
          />
          <p class="form_danger_text">{{ formError.passwordCheck }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="password_check">이름</label>
          <input
            class="form_input"
            type="text"
            id="name"
            name="name"
            v-model="formData.name"
            required
          />
          <p class="form_danger_text">{{ formError.name }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="nickname">지역</label>
          <button @click="setShowOption" class="form_select" type="button">
            <div class="form_select_content">
              {{ formData.region }}
              <span class="search-form__hightlight-gray">
                <font-awesome-icon :icon="['fas', 'sort-down']" />
              </span>
            </div>
            <ul v-if="showOption" class="form_select_options">
              <li v-for="option in options" :key="option.id" :value="option.id">
                <button @click="setSelectedRegion(option.name)" class="form_select_option">
                  {{ option.name }}
                </button>
              </li>
            </ul>
          </button>
          <p class="form_danger_text">{{ formError.region }}</p>
        </div>
        <div class="form_group">
          <label class="form_label" for="isHost">회원 타입</label>
          <button @click="setShowMemberOption" class="form_select" type="button">
            <div class="form_select_content">
              {{ formData.isHost }}
              <span class="search-form__hightlight-gray">
                <font-awesome-icon :icon="['fas', 'sort-down']" />
              </span>
            </div>
            <ul v-if="showMemberOption" class="form_select_options">
              <li v-for="option in memberOptions" :key="option.id" :value="option.id">
                <button @click="setSelectedMemberType(option.name)" class="form_select_option">
                  {{ option.name }}
                </button>
              </li>
            </ul>
          </button>
          <p class="form_danger_text">{{ formError.type }}</p>
        </div>
        <div class="form_group">
          <button @click="submitForm" class="form_btn" type="button">회원가입</button>
          <p class="form_text">
            이미 회원이신가요?
            <RouterLink :to="{ name: 'memberLogin' }">
              <span class="form_text_highlight">로그인</span>
            </RouterLink>
          </p>
        </div>
      </form>
    </div>
  </main>
</template>

<style scoped>
a {
  text-decoration: none;
  color: inherit;
}

a:visited {
  text-decoration: none;
  color: inherit;
}

button {
  cursor: pointer;
}

.container {
  width: 100%;
  min-height: calc(100vh - 80px - 200px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px 0px;
  padding-bottom: 360px;
}

.form_title {
  font-size: 28px;
  font-weight: bold;

  text-align: center;
  margin-bottom: 36px;
}

.form_group {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  margin-top: 4px;
}

.form_label {
  font-size: 16px;
  margin-bottom: 4px;
}

.form_input {
  background-color: #e9ecef;

  border: none;
  border-radius: 10px;

  width: 400px;
  height: 44px;
  padding: 0px 12px;
  font-size: 14px;
}

.form_input:focus {
  border: none;
  outline: 2px solid rgba(233, 236, 239, 0.8);
}

.form_select {
  position: relative;
  background-color: #e9ecef;

  border: none;
  border-radius: 10px;

  width: 200px;
  height: 44px;
  padding: 0px 12px;
  font-size: 14px;
}

.form_select_content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
}

.form_select_options {
  position: absolute;
  top: 50px;
  left: 0px;
  width: 200px;
  border: 1px solid var(--gray-medium);
  border-radius: 8px;
  box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  z-index: 1;
}

.form_select_option {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 0px 8px;
  width: 100%;
  font-size: 14px;
  line-height: 40px;
  background-color: var(--white);
  border: none;
  cursor: pointer;
}

.form_select_option:hover {
  background-color: var(--blue-light);
}

.form_danger_text {
  height: 16px;
  font-size: 14px;
  color: var(--red-medium);
  text-align: left;
}

.form_btn {
  background-color: #4bc9e5;
  color: white;

  border: none;
  border-radius: 10px;

  width: 400px;
  height: 44px;

  font-size: 16px;
  font-weight: bold;

  margin-top: 16px;
}

.form_btn:hover {
  outline: 2px solid rgb(75, 201, 229, 0.5);
}

.form_text {
  color: #666d75;

  margin-top: 8px;
  width: 100%;
  text-align: center;
  font-size: 14px;
}

.form_text_highlight {
  color: #4bc9e5;
}
</style>
