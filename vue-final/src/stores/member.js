import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/member";
import { httpStatusCode } from "@/utils/http-status";

import axios from "axios";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const router = useRouter();

    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userId = ref(false);
    const isHost = ref(false);
    const isValidToken = ref(false);

    const setHost = (type) => {
      isHost.value = type;
    };

    const userLogin = async (loginUser) => {
      await userConfirm(
        loginUser,
        async (response) => {
          if (response.status === httpStatusCode.CREATE) {
            console.log("로그인 성공!!!!");
            let { data } = response;
            let accessToken = data["accessToken"];
            let refreshToken = data["refreshToken"];

            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            userId.value = data["userId"];
            isHost.value = data["isHost"];

            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);

            const getMember = async () => {
              try {
                const response = await axios.get(`http://localhost:80/member`, {
                  headers: {
                    Authorization: `${sessionStorage.getItem("accessToken")}`,
                  },
                });
                console.log("login==>getMember: ", response);
                isHost.value = response.data.host;
                console.log(isHost);
              } catch (error) {
                console.log(error);
              }
            };

            getMember();
          }
        },
        (error) => {
          console.log("로그인 실패!!!!");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
          console.error(error);
        }
      );
    };

    const getUserInfo = async (token) => {
      let decodeToken = jwtDecode(token);
      console.log(decodeToken);
      await findById(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userId.value = response.data.userInfo;
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.error(
            "g[토큰 만료되어 사용 불가능.] : ",
            error.response.status,
            error.response.statusText
          );
          isValidToken.value = false;

          await tokenRegenerate();
        }
      );
    };

    const tokenRegenerate = async () => {
      await tokenRegeneration(
        JSON.stringify(userId.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userId.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                isLogin.value = false;
                userId.value = null;
                isValidToken.value = false;
                router.push({ name: "user-login" });
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                userId.value = null;
              }
            );
          }
        }
      );
    };

    const userLogout = async () => {
      console.log("로그아웃 아이디 : " + userId.value);
      await logout(
        userId.value,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            userId.value = null;
            isValidToken.value = false;

            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
          } else {
            console.error("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return {
      isLogin,
      isHost,
      isLoginError,
      userId,
      isValidToken,
      setHost,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
    };
  },
  {
    persist: true,
  }
);
