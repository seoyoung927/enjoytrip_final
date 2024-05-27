<script setup>
import { RouterView } from "vue-router";
import Footer from "./components/common/Footer.vue";
</script>

<template>
  <RouterView />
  <Footer></Footer>
</template>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
}
</style>
