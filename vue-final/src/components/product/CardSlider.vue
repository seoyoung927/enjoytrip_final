<script setup>
import axios from "axios";
import { defineProps, ref, computed, onMounted, onUnmounted } from "vue";
import { RouterLink } from "vue-router";

const props = defineProps({
  cardType: {
    type: String,
    default: "product",
  },
  cards: Array,
});

const currentIndex = ref(0);
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

onMounted(() => {
  updateNumVisibleCards();
  window.addEventListener("resize", updateNumVisibleCards);
  console.log(props.cards);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateNumVisibleCards);
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

const visibleCards = computed(() => {
  const start = currentIndex.value;
  const end = start + numVisibleCards.value;
  return props.cards.slice(start, end);
});

const showPrevButton = computed(() => currentIndex.value > 0);
const showNextButton = computed(
  () => currentIndex.value < props.cards.length - numVisibleCards.value
);
</script>

<template>
  <div class="card-slider">
    <button v-if="showPrevButton" class="card-slider-button left" @click="prevSlide">
      <font-awesome-icon :icon="['fas', 'chevron-left']" />
    </button>
    <transition-group name="slide" tag="ul" class="card-slider-list">
      <li v-for="card in visibleCards" :key="card.pid" class="card-slider-item">
        <div class="card">
          <img
            class="card-img"
            :src="
              card.imagePath.startsWith('http://') || card.imagePath.startsWith('https://')
                ? card.imagePath
                : `http://localhost:80/${card.imagePath}`
            "
            :alt="card.title"
          />
          <div class="card-content">
            <div>
              <p class="card-content-title">
                {{ card.title.length > 12 ? card.title.slice(0, 12) + "..." : card.title }}
              </p>
              <p class="card-content-intro">
                {{
                  card.intro
                    ? card.intro
                    : card.overview.length > maxWordLength
                    ? card.overview.slice(0, maxWordLength) + "..."
                    : card.overview
                }}
              </p>
            </div>
            <div>
              <RouterLink
                v-if="props.cardType == 'product'"
                :to="{ name: 'productDetail', params: { id: card.pid } }"
              >
                <button type="button" class="card-content-button">둘러보기</button>
              </RouterLink>
              <RouterLink v-else :to="{ name: 'search', query: { selectedItem: card.title } }">
                <button type="button" class="card-content-button">둘러보기</button>
              </RouterLink>
            </div>
          </div>
        </div>
      </li>
    </transition-group>
    <button v-if="showNextButton" class="card-slider-button right" @click="nextSlide">
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

.card-slider-button {
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

.card-slider-button:hover {
  box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
}

.card-slider-button.left {
  left: -20px;
}

.card-slider-button.right {
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

.card {
  position: relative;
  width: 100%;
  aspect-ratio: 3 / 4;
  border-radius: 10px;
  overflow: hidden;
  transition: background-color 0.5s ease;
  background-color: var(--gray-dark);
}

.card-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease, filter 0.5s ease;
}

.card-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: absolute;
  top: 0;
  left: 0;
  padding: 32px 24px;
  padding-bottom: 48px;
  width: 100%;
  height: 100%;
  color: var(--white);
  z-index: 1;
  background: linear-gradient(to bottom, rgba(0, 0, 0, 0.5), transparent);
}

.card:hover {
  background-image: none;
  background-color: rgba(255, 255, 255, 0.5);
}

.card:hover .card-img {
  transform: scale(1.05);
  filter: blur(2px);
}

.card-content-title {
  font-size: 24px;
  font-weight: bold;
}

.card-content-intro {
  margin-top: 8px;
  font-size: 16px;
}

.card-content-button {
  background-color: var(--white);
  width: 96px;
  height: 40px;
  font-size: 14px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
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
