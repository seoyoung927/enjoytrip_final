<template>
  <div class="loading">
    <div class="spinner"></div>
    <p>Loading...</p>
  </div>
</template>

<script setup></script>

<style scoped>
.loading {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}
.spinner {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 4px solid var(--gray-light);
  border-top: 4px solid var(--primary);
  animation: spin 1s linear infinite;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
.loading p {
  color: var(--gray-medium);
  font-size: 20px;
  margin-top: 10px;
}
</style>
