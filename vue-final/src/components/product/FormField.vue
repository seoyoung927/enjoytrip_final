<script setup>
import { computed } from "vue";
  
const props = defineProps({
    label: String,
    value: [String, Number],
    error: String,
    type: {
        type: String,
        default: "text",
    },
    id: String,
    name: String,
});

const emit = defineEmits(["input"]);
const inputType = computed(() => (props.type === "textarea" ? "textarea" : "input"));
const updateValue = (event) => {
    emit("input", event.target.value);
};
</script>

<template>
    <div class="form_group">
        <label class="form_label" :for="id">{{ label }}</label>
        <component
        :is="inputType"
        :class="[inputType === 'textarea' ? 'form_textarea' : 'form_input']"
        :id="id"
        :name="name"
        :value="value"
        :type="type"
        @input="updateValue"
        required
        />
        <span class="form_error">{{ error }}</span>
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

.form_textarea {
  width: 400px;
  height: 200px;
  padding: 8px;
}

.form_error {
  color: red;
  font-size: 14px;
  margin-top: 4px;
}
</style>
  