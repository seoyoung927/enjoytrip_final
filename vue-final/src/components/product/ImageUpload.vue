<script setup>
import { watch } from "vue";
  
const props = defineProps({
    files: Array,
    error: String,
    previews: Array,
});
  
const emit = defineEmits(["update:files"]);
  
const handleImageUpload = (event) => {
    const files = Array.from(event.target.files);
    if (files.length > 4) {
      alert("최대 4장까지 업로드 가능합니다");
    } else {
      emit("update:files", files);
    }
};
  
watch(() => props.previews, (newVal) => {
    if (newVal.length > 4) {
      alert("최대 4장까지 업로드 가능합니다");
    }
    console.log(newVal);
});
</script>

<template>
    <div class="form_group">
        <label class="form_label" for="images">이미지</label>
        <input
            type="file"
            class="form_input"
            accept="image/*"
            multiple
            @change="handleImageUpload"
        />
        <span class="form_error">{{ error }}</span>
        <div class="form_image_preview" v-for="(image, index) in previews" :key="index">
            <img :src="image" alt="이미지 미리보기" />
        </div>
    </div>
</template>

  
<style scoped>
.form_group {
  display: flex;
  flex-direction: column;
  margin-top: 8px;
}

.form_label {
  margin-bottom: 4px;
  font-size: 16px;
}

.form_input {
  width: 400px;
  height: 40px;
  padding: 0 8px;
}

.form_image_preview {
  background-color: var(--gray-200);
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 4px;
}

.form_image_preview img {
  max-height: 100%;
  max-width: 100%;
  aspect-ratio: 4/3;
}

.form_error {
  color: red;
  font-size: 14px;
  margin-top: 4px;
}
</style>
  