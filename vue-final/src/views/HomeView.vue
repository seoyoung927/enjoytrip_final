<script setup>
import MainHeader from "@/components/common/MainHeader.vue";
</script>
<template>
  <MainHeader />
  <section class="banner">
    <img class="banner__img" src="../assets/img/Seoul_big.jpg" alt="danang" />
    <div class="banner__title">
      지금 EnjoyTrip과 함께 우리 지역의 관광지를 알아보고 나만의 여행 계획을 만들어 보세요!
    </div>
  </section>
  <section class="wrapper">
    <div class="card-list-wrapper">
      <h2 class="card-list-title">지역별 여행지🏝️</h2>
      <ul class="card-list">
        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '서울' } }">
            <div>
              <img class="card__img" src="../assets/img/Seoul.jpg" alt="seoul" />
              <div class="card__info">
                <h3 class="card__info-title">서울</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '부산' } }">
            <div>
              <img class="card__img" src="../assets/img/Busan.jpg" alt="seoul" />
              <div class="card__info">
                <h3 class="card__info-title">부산</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '제주' } }">
            <div>
              <img class="card__img" src="../assets/img/Jeju.jpg" alt="seoul" />
              <div class="card__info">
                <h3 class="card__info-title">제주</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '경주' } }">
            <div>
              <img class="card__img" src="../assets/img/Gyeongju.jpg" alt="seoul" />
              <div class="card__info">
                <h3 class="card__info-title">경주</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '대전' } }">
            <div>
              <img class="card__img" src="../assets/img/Daejeon.jpg" alt="daejeon" />
              <div class="card__info">
                <h3 class="card__info-title">대전</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '여수' } }">
            <div>
              <img class="card__img" src="../assets/img/Yeosu.jpg" alt="yeosu" />
              <div class="card__info">
                <h3 class="card__info-title">여수</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '전주' } }">
            <div>
              <img class="card__img" src="../assets/img/Jeonju.jpg" alt="jeonju" />
              <div class="card__info">
                <h3 class="card__info-title">전주</h3>
              </div>
            </div>
          </router-link>
        </li>

        <li>
          <router-link class="card" :to="{ path: '/enjoytrip/search', query: { keyword: '강릉' } }">
            <div>
              <img class="card__img" src="../assets/img/Gangneung.jpg" alt="gangneung" />
              <div class="card__info">
                <h3 class="card__info-title">강릉</h3>
              </div>
            </div>
          </router-link>
        </li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
.banner {
  display: flex;
  align-items: center;
  position: relative;
  padding: 40px;
  width: 100%;
  height: 400px;
  color: #fff;
  background-image: linear-gradient(
    transparent,
    transparent 30%,
    rgba(0, 0, 0, 0.2),
    transparent 70%,
    transparent
  );
}

.banner__img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: brightness(90%);
  z-index: -1;
}

@keyframes slideup {
  0% {
    opacity: 0;
    transform: translateY(40px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.banner__title {
  display: inline-block;
  height: unset;
  margin: 0 auto;
  font-size: 24px;
  font-weight: bold;
  word-break: keep-all;
  text-wrap: balance;
  text-shadow: 0 0 12px rgba(0, 0, 0, 0.9);
  overflow: visible;
  opacity: 0;
  animation: slideup 1s ease forwards;
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 60px 120px;
  width: 100%;
}

.card-list-wrapper {
  width: 100%;
  max-width: 1280px;
  max-width: 1024px; /* Ensure wrapper doesn't exceed a certain width */
}

.card-list-title {
  padding-bottom: 16px;
}

.card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
}

.card-list li {
  display: flex;
  position: relative;
  width: calc((100% - 160px) / 4);
  aspect-ratio: 1;
}

.card {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  position: relative;
  padding: 20px;
  overflow: hidden;
  text-decoration: none;
  width: 100%;
  height: 100%;
  color: var(--white);
  border-radius: 8px;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  cursor: pointer;
  transition: transform 0.5s ease background-color 0.5s ease;
  background-image: linear-gradient(142deg, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0) 65%);
  background-color: transparent;
  box-sizing: border-box;
}

.card__img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  filter: brightness(80%);
  transition: all 0.5s ease;
}

.card:hover {
  background-image: none;
  background-color: rgba(255, 255, 255, 0.5);
}

.card:hover .card__img {
  transform: scale(1.05);
  filter: blur(2px);
}

.card__info-title {
  margin: 0;
  color: var(--white);
  text-decoration: none;
}

.card:hover .card__info-title {
  color: var(--black);
}

a {
  text-decoration: none;
}

@media screen and (max-width: 1024px) {
  .wrapper {
    padding: 60px 60px;
  }

  .card-list {
    gap: 20px;
  }

  .card-list li {
    width: calc((100% - 60px) / 3);
  }
}

@media screen and (max-width: 600px) {
  .card-list li {
    width: calc((100% - 40px) / 2);
  }
}
</style>
