import axios from "axios";

const { VITE_API_BASE_URL } = "http://localhost/";

function localAxios() {
  const instance = axios.create({
    baseURL: "http://localhost/",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

function localAuthAxios() {
  const instance = axios.create({
    baseURL: "http://localhost/",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      Authorization: `${sessionStorage.getItem("accessToken")}`,
    },
  });
  return instance;
}

function localFormAuthAxios() {
  console.log("===> accessToken:   ", sessionStorage.getItem("accessToken"));
  const instance = axios.create({
    baseURL: "http://localhost/",
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `${sessionStorage.getItem("accessToken")}`,
    },
  });
  return instance;
}

export { localAxios, localAuthAxios, localFormAuthAxios };
