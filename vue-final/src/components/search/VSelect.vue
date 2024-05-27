<template>
  <button @click="setShowOption" class="search-form__button-select" type="button">
    <div class="search-form__button-content">
      {{ selectedOption }}
      <span class="search-form__hightlight-gray">
        <font-awesome-icon :icon="['fas', 'sort-down']" />
      </span>
    </div>
    <ul v-if="showOption" class="search-form__options">
      <li v-for="option in props.options" :key="option.id" :value="option.id">
        <button class="search-form__option" type="button" @click="handleSelectChange(option.name)">
          {{ option.name }}
        </button>
      </li>
    </ul>
  </button>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

const props = defineProps({
  options: Array,
  selectedOption: String,
});

const emit = defineEmits(["setSelectedOption"]);

const handleSelectChange = (optionName) => {
  emit("setSelectedOption", optionName);
};

const showOption = ref(false);
const setShowOption = () => {
  showOption.value = !showOption.value;
};
</script>

<style scoped>
.search-form__button-select {
  position: relative;
  padding: 0px 8px;
  width: 100px;
  height: 48px;
  font-size: 16px;
  background-color: var(--white);
  border: none;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  cursor: pointer;
  /* 말줄임 */
  white-space: nowrap;
  text-overflow: ellipsis;
}

.search-form__button-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0px 4px;
  padding-bottom: 4px;
  width: 100%;
  height: 100%;
}

.search-form__hightlight-gray {
  display: flex;
  justify-content: center;
  align-items: flex;
  padding-bottom: 4px;
  color: var(--gray-medium);
}

.search-form__options {
  position: absolute;
  top: 50px;
  left: 0px;
  width: 120px;
  border: 1px solid var(--gray-medium);
  border-radius: 8px;
  box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
  overflow: hidden;
}

.search-form__option {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 0px 8px;
  width: 100%;
  font-size: 16px;
  line-height: 40px;
  background-color: var(--white);
  border: none;
  cursor: pointer;
}

.search-form__option:hover {
  background-color: var(--blue-light);
}
</style>
