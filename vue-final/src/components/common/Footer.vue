<script setup></script>

<template>
  <footer class="footer">
    <div class="footer-content">
      <p>© 2024 SSAFY. All Rights Reserved.</p>
      <nav class="footer-nav">
        <ul>
          <li><a href="#home">Home</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#services">Services</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
      </nav>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  margin-top: 40px;
  width: 100%;
  height: 160px;
  background-color: var(--gray-light);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
}

.footer-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 80%;
  max-width: 1024px;
  line-height: 4;
}

.footer-content p {
}

.footer-nav ul {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}

.footer-nav a {
  text-decoration: none;
  color: var(--text-color, #333);
}

.footer-nav a:hover {
  text-decoration: underline;
}

/* 768px 이상에서 카드 2개 표시 */
@media (min-width: 768px) {
  .footer-content {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
