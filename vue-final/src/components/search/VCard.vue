<script setup>
import { ref } from "vue";

const props = defineProps({
  place: Object,
});

//하트(클릭)
const clicked = ref(false);
const setClicked = () => {
  clicked.value = !clicked.value;
};
///////////////////////////////////////////////
</script>

<template>
  <div class="search-result__card">
    <div class="search-result__card-content-wrapper-1">
      <img
        v-if="props.place?.first_image"
        :src="props.place?.first_image"
        class="search-result__card-image"
      />
      <div v-else class="no-image">이미지 준비 중입니다.</div>
    </div>
    <div class="search-result__card-content-wrapper-2">
      <p class="search-result__card-title">
        {{ props.place?.title }}
      </p>
      <!-- <button class="search-result__card-button-heart" @click="setClicked" v-if="!clicked">
        <font-awesome-icon :icon="['far', 'heart']" />
      </button>
      <button class="search-result__card-button-heart" @click="setClicked" v-if="clicked">
        <font-awesome-icon :icon="['fas', 'heart']" />
      </button> -->
    </div>
    <div class="search-result__card-content-wrapper-3">
      <p class="search-result__card-address">{{ props.place?.addr1 }}</p>
    </div>
  </div>
</template>

<style scoped>
.search-result__card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 24px;
  width: 340px;
  cursor: pointer;
}

.search-result__card-image {
  width: 280px;
  aspect-ratio: 12/8;
}

.no-image {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 280px;
  aspect-ratio: 12/8;
  background-color: var(--gray-light);
}

.search-result__card-content-wrapper-2 {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-top: 8px;
  width: 280px;
}

.search-result__card-title {
  margin-bottom: 4px;
  font-weight: bold;
  color: var(--gray-dark);
}

.search-result__card-button-heart {
  font-size: 18px;
  color: var(--red-medium);
  background-color: inherit;
  border: none;
  cursor: pointer;
}

.search-result__card-content-wrapper-3 {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 280px;
}

.search-result__card-address {
  font-size: 14px;
  color: var(--gray-medium);
}
</style>
