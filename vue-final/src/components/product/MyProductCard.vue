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
          <RouterLink :to="{ name: 'productModify', params: { id: card?.pid } }">
            <button type="button" class="card-content-button">둘러보기</button>
          </RouterLink>
        </div>
      </div>
    </div>
</template>
  
<style scoped>
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
</style>
  