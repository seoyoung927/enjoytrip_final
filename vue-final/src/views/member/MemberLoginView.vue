<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
import { useRouter, RouterLink } from "vue-router";
import { reactive } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { localAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();

const router = useRouter();
const memberStore = useMemberStore();
const { isLogin, isHost } = storeToRefs(memberStore);
const { setHost, userLogin } = memberStore;

//form 전송
const formData = reactive({
  userId: "",
  password: "",
});
const formError = reactive({
  userId: "",
  password: "",
});
const submitForm = () => {
  //validata form
  const isValidate = validate();
  if (isValidate) {
    //전송
    console.log("전송");
    login();
  }
};
const validate = () => {
  console.log("validate: ", formData);
  let flag = true;
  if (!formData.userId || formData.userId === " ") {
    formError.userId = "아이디를 입력해주세요";
    flag = false;
  } else {
    formError.userId = "";
  }
  if (!formData.password) {
    formError.password = "비밀번호를 입력해주세요";
    flag = false;
  } else {
    formError.password = "";
  }
  return flag;
};
const login = async () => {
  await userLogin(formData).then(async () => {
    let token = sessionStorage.getItem("accessToken");
    console.log("login: ", token);
    console.log("isLogin: " + isLogin.value);
    if (isLogin.value) {
      //getUserInfo(token);
      router.replace({ name: "home" });
    } else {
      formError.userId = "아이디, 비밀번호를 확인해주세요.";
      formError.password = "아이디, 비밀번호를 확인해주세요.";
    }
  });
};
////////////////////////////////////////////
</script>

<template>
  <MainHeader></MainHeader>
  <main>
    <div class="container">
      <form class="form">
        <h2 class="form_title">로그인</h2>
        <div class="form_group">
          <label class="form_label" for="text">아이디</label>
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
          <button @click="submitForm" class="form_btn" type="button">로그인</button>
          <p class="form_text">
            아직 회원이 아니신가요?
            <RouterLink :to="{ name: 'memberRegister' }">
              <span class="form_text_highlight">회원가입</span>
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
  padding: 80px 0px;
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
