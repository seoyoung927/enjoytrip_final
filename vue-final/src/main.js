import "./assets/main.css";

import { createApp } from "vue";
import App from "./App.vue";

//라우터
import router from "./router";
//pinia
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
// CSS
import "./assets/css/colors.css";
//icon
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { far } from "@fortawesome/free-regular-svg-icons";
import { fas } from "@fortawesome/free-solid-svg-icons";
library.add(far);
library.add(fas);

const app = createApp(App);

// 토큰 초기화
app.use(pinia);
app.use(router);
app.use(router).component("font-awesome-icon", FontAwesomeIcon);

router.isReady().then(() => {
  app.mount("#app");
});
