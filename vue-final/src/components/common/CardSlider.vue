<script setup>
import { defineProps, ref, computed, onMounted, onUnmounted } from "vue";
import ProductCard from '../product/ProductCard.vue';
import MyProductCard from '../product/MyProductCard.vue';
import PlaceCard from '../place/PlaceCard.vue';

const props = defineProps({
  cardType: {
    type: String,
    default: "product",
  },
  cards: Array,
});

//resize
const numVisibleCards = ref(4);
const maxWordLength = ref(50);
const updateNumVisibleCards = () => {
  const width = window.innerWidth;
  if (width >= 1200) {
    currentIndex.value = 0;
    numVisibleCards.value = 4;
    maxWordLength.value = 50;
  } else if (width >= 992) {
    currentIndex.value = 0;
    numVisibleCards.value = 3;
    maxWordLength.value = 50;
  } else if (width >= 768) {
    currentIndex.value = 0;
    numVisibleCards.value = 2;
    maxWordLength.value = 50;
  } else {
    currentIndex.value = 0;
    numVisibleCards.value = 1;
    maxWordLength.value = 30;
  }
};

//이전, 다음 버튼
const currentIndex = ref(0);
const visibleCards = computed(() => {
  const start = currentIndex.value;
  const end = start + numVisibleCards.value;
  return props.cards.slice(start, end);
});

const prevSlide = () => {
  if (currentIndex.value > 0) {
    currentIndex.value -= numVisibleCards.value;
  }
};
const nextSlide = () => {
  if (currentIndex.value < props.cards.length - numVisibleCards.value) {
    currentIndex.value += numVisibleCards.value;
  }
};
const showPrevButton = computed(() => currentIndex.value > 0);
const showNextButton = computed(
  () => currentIndex.value < props.cards.length - numVisibleCards.value
);

//mount, unmount
onMounted(() => {
  updateNumVisibleCards();
  window.addEventListener("resize", updateNumVisibleCards);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateNumVisibleCards);
});
</script>

<template>
  <div class="card-slider">
    <button v-if="showPrevButton" class="card-slider-btn left" @click="prevSlide">
      <font-awesome-icon :icon="['fas', 'chevron-left']" />
    </button>
    <ul class="card-slider-list">
      <li v-for="card in visibleCards" :key="card.pid" class="card-slider-item">
        <component :is="cardType === 'product' ? ProductCard : cardType === 'myProduct' ? MyProductCard : PlaceCard" :card="card" :maxWordLength="maxWordLength" />
      </li>
    </ul>
    <button v-if="showNextButton" class="card-slider-btn right" @click="nextSlide">
      <font-awesome-icon :icon="['fas', 'chevron-right']" />
    </button>
  </div>
</template>

<style scoped>
.card-slider {
  position: relative;
  display: flex;
  width: 50%;
  min-width: none;
}

.card-slider-btn {
  position: absolute;
  top: calc(50% - 18px);
  background-color: var(--white);
  width: 40px;
  height: 40px;
  color: var(--gray-dark);
  border: 1px solid var(--gray-light);
  border-radius: 100%;
  padding: 10px;
  cursor: pointer;
  z-index: 2;
}

.card-slider-btn:hover {
  box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
}

.card-slider-btn.left {
  left: -20px;
}

.card-slider-btn.right {
  right: -20px;
}

.card-slider-list {
  display: flex;
  padding: 0;
  margin: 0;
  gap: 16px;
  width: 100%;
  list-style: none;
  overflow: hidden;
  transition: background-color 0.5s ease; /* Add box-shadow transition */
}

.card-slider-item {
  flex: 0 0 auto;
  width: 100%;
}

/* .slide-enter-active, .slide-leave-active {
  transition: transform 0.3s ease-in-out;
} */

/* .slide-enter, .slide-leave-to {
  transform: translateX(-200%);
} */

/* 768px 이상에서 카드 2개 표시 */
@media (min-width: 768px) {
  .card-slider {
    width: 100%;
  }

  .card-slider-item {
    width: calc((100% - 16px) / 2);
  }
}

/* 992px 이상에서 카드 3개 표시 */
@media (min-width: 992px) {
  .card-slider-item {
    width: calc((100% - 32px) / 3);
  }
}

/* 1200px 이상에서 카드 4개 표시 */
@media (min-width: 1200px) {
  .card-slider-item {
    width: calc((100% - 48px) / 4);
  }
}
</style>
