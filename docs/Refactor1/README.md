# 1. Refactor: CardSlider

현재 CardSlider는 1. 단일 책임의 원칙을 만족하지 않고, 2. 도메인(product, place)에 따른 차이를 조건부 렌더링을 통해 구현한다. ⇒ 코드가 너무 복잡하다. / 추후 요구사항이 변경되었을 때 유지보수가 어렵다.

**초기코드**

```jsx
<script setup>
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
    <button v-if="showPrevButton" class="card-slider-btn left" @click="prevSlide">
      <font-awesome-icon :icon="['fas', 'chevron-left']" />
    </button>
    <ul class="card-slider-list">
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
	                 card.overview.length > maxWordLength
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
    </ul>
    <button v-if="showNextButton" class="card-slider-btn right" @click="nextSlide">
      <font-awesome-icon :icon="['fas', 'chevron-right']" />
    </button>
  </div>
</template>

<style scoped>
/* 스타일 */
</style>

```

### 🛠️단일 책임의 원칙

우선 현재 코드는 단일 책임의 원칙을 만족하지 않는다. 하나의 카드 슬라이더 내에 **1) 카드를 렌더링 하는 로직, 2) 슬라이더 동작 로직** 등의 한번에 구현되어 있다. 이를 별도의 컴포넌트로 분리해보자.

1. CardSlider.vue: 슬라이더 동작 로직

```jsx
<script setup>
import { defineProps, ref, computed, onMounted, onUnmounted } from "vue";
import VCard from './VCard.vue';

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

const getRouterLink = (card, props) => {
  if (props.cardType === 'product') {
    return {
      name: 'productDetail', params: { id: card?.pid }
    };
  } else {
    return {
      name: 'search', query: { selectedItem: card?.title }
    };
  }
};
</script>

<template>
  <div class="card-slider">
    <button v-if="showPrevButton" class="card-slider-btn left" @click="prevSlide">
      <font-awesome-icon :icon="['fas', 'chevron-left']" />
    </button>
    <ul class="card-slider-list">
      <li v-for="card in visibleCards" :key="card.pid" class="card-slider-item">
        <VCard :routerLink="getRouterLink(card, props)" :pid="card?.pid" :title="card?.title" :intro="card?.intro" :imagePath="card?.imagePath" :maxWordLength="maxWordLength">
        </VCard>
      </li>
    </ul>
    <button v-if="showNextButton" class="card-slider-btn right" @click="nextSlide">
      <font-awesome-icon :icon="['fas', 'chevron-right']" />
    </button>
  </div>
</template>

<style scoped>
/* 스타일 */
</style>
```

2. VCard.vue: 카드를 렌더링 하는 로직
<script setup>
import { defineProps } from "vue";
import { RouterLink } from "vue-router";

const props = defineProps({
pid: Number,
imagePath: String,
title: String,
intro: String,
maxWordLength: Number,
});
</script>

<template>
    <div class="card">
      <img
        class="card-img"
        :src="
          imagePath.startsWith('http://') || imagePath.startsWith('https://')
            ? imagePath
            : `http://localhost:80/${imagePath}`
        "
        :alt="title"
      />
      <div class="card-content">
        <div>
          <p class="card-content-title">
            {{ title.length > 12 ? title.slice(0, 12) + "..." : title }}
          </p>
          <p class="card-content-intro">
            {{
              intro.length > maxWordLength
                ? intro.slice(0, maxWordLength) + "..."
                : intro
            }}
          </p>
        </div>
        <div>
          <RouterLink
            v-if="cardType == 'product'"
            :to="{ name: 'productDetail', params: { id: pid } }"
          >
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
          <RouterLink v-else :to="{ name: 'search', query: { selectedItem: title } }">
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
        </div>
      </div>
    </div>
</template>
  
<style scoped>
/* 스타일 */
</style> 
```

```jsx
<script setup>
import { defineProps } from "vue";

const props = defineProps({
  pid: Number,
  imagePath: String,
  title: String,
  intro: String,
  maxWordLength: Number,
  routerLink: Object,
});
</script>

<template>
    <div class="card">
      <img
        class="card-img"
        :src="
          imagePath.startsWith('http://') || imagePath.startsWith('https://')
            ? imagePath
            : `http://localhost:80/${imagePath}`
        "
        :alt="title"
      />
      <div class="card-content">
        <div>
          <p class="card-content-title">
            {{ title.length > 12 ? title.slice(0, 12) + "..." : title }}
          </p>
          <p class="card-content-intro">
            {{
              intro.length > maxWordLength
                ? intro.slice(0, maxWordLength) + "..."
                : intro
            }}
          </p>
        </div>
        <div>
          <RouterLink :to="routerLink">
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
        </div>
      </div>
    </div>
</template>
```

### 🛠️도메인에 따른 컴포넌트의 확장

현재 컴포넌트는 **`props`**를 사용하여 **`product`**와 **`search`**를 받고, 이에 따라 조건부 렌더링을 수행하여 컴포넌트를 재사용하고 있다. 그러나 요구사항이 변경될 때마다 **`props`**에 의존하거나 새로운 **`props`**를 추가하는 방식은 코드를 복잡하게 만들 수 있다. 이러한 문제를 해결하기 위해서는 외부에 주도권을 넘겨야 한다. 이를 통해 외부에서 많은 결정을 내릴 수 있도록 하여 컴포넌트를 확장할 수 있도록 해야한다.
⇒컴포넌트가 확장 가능하도록 합성 가능한 구조를 만들어야 한다.

CardSlider를 특정 도메인에 종속된 것이 아닌 common으로 만들고, Product와 Search Card를 분리했다.

1. CardSlider.vue

```jsx
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
```

2. ProductCard.vue

```jsx
<script setup>
import { defineProps } from "vue";

const props = defineProps({
  card: Object,
  maxWordLength: Number
});
</script>

<template>
    <div class="card">
      <img
        class="card-img"
        :src="
          card?.imagePath.startsWith('http://') || card?.imagePath.startsWith('https://')
            ? card?.imagePath
            : `http://localhost:80/${card.imagePath}`
        "
        :alt="card?.title"
      />
      <div class="card-content">
        <div>
          <p class="card-content-title">
            {{ card?.title.length > 12 ? card?.title.slice(0, 12) + "..." : card?.title }}
          </p>
          <p class="card-content-intro">
            {{
              card?.intro.length > maxWordLength
                ? card?.intro.slice(0, maxWordLength) + "..."
                : card?.intro
            }}
          </p>
        </div>
        <div>
          <RouterLink :to="{name: 'productDetail', params: { id: card?.pid }}">
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
        </div>
      </div>
    </div>
</template>
```

3. PlaceCard.vue

```jsx
<script setup>
import { defineProps } from "vue";

const props = defineProps({
  card: Object,
  maxWordLength: Number
});
</script>

<template>
    <div class="card">
      <img
        class="card-img"
        :src="
          card?.imagePath.startsWith('http://') || card?.imagePath.startsWith('https://')
            ? card?.imagePath
            : `http://localhost:80/${card.imagePath}`
        "
        :alt="card?.title"
      />
      <div class="card-content">
        <div>
          <p class="card-content-title">
            {{ card?.title.length > 12 ? card?.title.slice(0, 12) + "..." : card?.title }}
          </p>
          <p class="card-content-intro">
            {{
              card?.overview.length > maxWordLength
                ? card?.overview.slice(0, maxWordLength) + "..."
                : card?.overview
            }}
          </p>
        </div>
        <div>
          <RouterLink :to="{name: 'search', query: { selectedItem: card?.title } }">
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
        </div>
      </div>
    </div>
</template>
```
