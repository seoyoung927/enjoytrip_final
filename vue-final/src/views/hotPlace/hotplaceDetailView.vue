<script setup>
import { useRouter } from "vue-router";
import MainHeader from "@/components/common/MainHeader.vue";
import { reactive, ref, onMounted, watch } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

// 스토어 가져오기
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

import { useRoute } from "vue-router";
import { localAuthAxios } from "@/utils/http-commons";

const localAuth = localAuthAxios();
const router = useRouter();
const route = useRoute();
const hotplaceId = route.params.id;

const goBack = () => {
  router.go(-1);
};

const card = ref({
  hid: "",
  userId: "",
  title: "",
  createdTime: "",
  intro: "",
  description: "",
  imagePath: "",
  comments: [],
});

// 댓글 데이터
const newComment = ref("");

const getHotplace = async () => {
  try {
    const response = await localAuth.get(`/hotplace/${hotplaceId}`);
    // 서버에서 데이터 가져오기
    console.log("==> getHotplace: ", response.data);
    card.value = response.data;
    card.value.comments.forEach(comment => {
      comment.isEditing = false;
      comment.editText = "";
      comment.showMenu = false;
    });
    console.log("===> card: ", card.value);
  } catch (error) {
    console.error("Error getProduct data:", error);
  }
};

const addComment = async () => {
  // if (newComment.value.trim() === "") {
  //   alert("댓글을 입력해주세요.");
  //   return;
  // }
  try {
    const response = await localAuth.post(`/hotplace/comment`, {
      userId: userId.value, // 현재 사용자의 userId를 사용
      content: newComment.value,
      hid: card.value.hid,
    });
    console.log("===>addComment: ", response);
    getHotplace();
    newComment.value = "";
  } catch (error) {
    console.error("Error adding comment:", error);
  }
};

const editComment = async(comment) => {
  comment.isEditing = true;
  comment.editText = comment.content;
};
  
const saveComment = async (cid, comment) => {
  // if (comment.editText.trim() === "") {
  //   alert("댓글을 입력해주세요.");
  //   return;
  // }
  try {
    await localAuth.patch(`/hotplace/comment`, {
      cid: cid,
      userId: userId.value, // 현재 사용자의 userId를 사용
      content: comment.editText,
      hid: card.value.hid,
    });
    comment.content = comment.editText;
    comment.isEditing = false;
  } catch (error) {
    console.error("Error saving comment:", error);
  }
};

const cancelEdit = (comment) => {
  comment.isEditing = false;
  comment.editText = comment.content;
};

const deleteComment = async (cid) => {
  try {
    await localAuth.delete(`/hotplace/comment/${cid}`);
    card.value.comments = card.value.comments.filter(c => c.cid !== cid);
  } catch (error) {
    console.error("Error deleting comment:", error);
  }
};

const toggleMenu = (comment) => {
  comment.showMenu = !comment.showMenu;
};

onMounted(() => {
  console.log("mounted!!!");
  getHotplace();
  console.log(userId.value);
});
</script>

<template>
  <MainHeader />
  <div class="container">
    <div class="detail-wrapper">
      <div class="goBack-wrapper">
        <button @click="goBack" class="goBack-btn">
          <font-awesome-icon :icon="['fas', 'angle-left']" />
        </button>
        <!-- <RouterLink :to="{ name: 'hotplaceModify' }">
          <button @click="edit" class="edit-btn">수정하기</button>
        </RouterLink> -->
      </div>
      <div class="detail-imgs">
        <div class="big-img">
          <img :src="`http://localhost:80${card.imagePath}`" alt="큰 이미지" />
        </div>
      </div>
      <div class="detail-contents">
        <h3 class="detail-title">{{ card.title }}</h3>
        <div class="detail-content-wrapper">
          <p class="detail-userId"><pre>{{ card.userId }}</pre></p>
          <p class="detail-time"><pre>{{ card.createdTime.split('T')[0] }}</pre></p>
        </div>
        <p class="detail-description"><pre>{{ card.description }}</pre></p>
      </div>
      <!-- 댓글 섹션 -->
      <div class="comments-section">
        <h3 class="add-comment-title">댓글</h3>
        <div class="add-comment">
          <textarea v-model="newComment"></textarea>
          <button class="add-btn" @click="addComment">댓글 달기</button>
        </div>
        <div class="comments-list">
          <div v-for="(comment, index) in card.comments" :key="comment.id" class="comment">
            <div :class="['comment-wrapper', { 'first-comment': index === 0 }]" >
              <div class="comment-content-wrapper">
                <div class="comment-userId">{{ comment.userId }}</div>
                <div class="comment-content">{{ comment.content }}</div>
                <div class="comment-time">{{ comment.createdTime.split('T')[0] }}</div>
              </div>
              <div v-if="comment.userId === userId" class="btn-wrapper">
                <button class="menu-btn" @click="toggleMenu(comment)">
                  <font-awesome-icon :icon="['fas', 'bars']" />
                </button>
                <div v-if="comment.showMenu" class="menu-dropdown">
                  <button class="dropdown-btn" @click="editComment(comment)">수정</button>
                  <button class="dropdown-btn" @click="deleteComment(comment.cid)">삭제</button>
                </div>
              </div>
            </div>
            <div class="edit-wrapper" v-if="comment.isEditing">
              <textarea v-model="comment.editText"></textarea>
              <div class="btn-wrapper">
                <button class="comment-btn" @click="saveComment(comment.cid, comment)">저장</button>
                <button class="comment-btn" @click="cancelEdit(comment)">취소</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 60px 60px;
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 80px - 200px);
}

.goBack-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 32px;
}

.goBack-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  left: 0;
  width: 32px;
  height: 32px;
  color: var(--gray-dark);
  border: none;
  border-radius: 100%;
  background-color: var(--white);
  cursor: pointer;
}

.goBack-btn:hover {
  background-color: var(--secondary-light);
}

.edit-btn {
  background-color: var(--secondary);
  color: var(--white);
  border: none;
  border-radius: 10px;
  width: 96px;
  height: 40px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.edit-btn:hover {
  outline: 4px solid var(--secondary-light);
}

.detail-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
}

.detail-imgs {
  display: flex;
  flex-direction: column;
  width: 70%;
}

.big-img {
  width: 100%;
  aspect-ratio: 4 / 3;
}

.big-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-contents {
  width: 100%;
}

.detail-content-wrapper{
  display: flex;
  margin-top: 8px;
}

.detail-title {
  margin-top: 64px;
  font-size: 22px;
  line-height: 1.8;
}

.detail-userId{
  margin-right: 16px;
}

.detail-time{

}

.detail-description {
  width: 100%;
  margin-top: 16px;
  font-size: 16px;
  line-height: 1.8;
}

.detail-description pre {
  white-space: pre-wrap;
  white-space: pre-wrap; /* CSS3 */
  white-space: -moz-pre-wrap; /* Mozilla */
  white-space: -pre-wrap; /* Opera 4-6 */
  white-space: -o-pre-wrap; /* Opera 7 */
  word-wrap: break-word; /* IE 5.5+ */
  max-width: 100%; /* Ensure it doesn't go beyond the container's width */
}

.detail-warning-title {
  margin-top: 64px;
  font-size: 22px;
  line-height: 1.8;
}

.detail-waring {
  margin-top: 16px;
  font-size: 16px;
  line-height: 1.8;
  white-space: pre-wrap;
}

.detail-warning pre {
  white-space: pre-wrap;
  white-space: pre-wrap; /* CSS3 */
  white-space: -moz-pre-wrap; /* Mozilla */
  white-space: -pre-wrap; /* Opera 4-6 */
  white-space: -o-pre-wrap; /* Opera 7 */
  word-wrap: break-word; /* IE 5.5+ */
  max-width: 100%; /* Ensure it doesn't go beyond the container's width */
}

.comments-section {
  width: 100%;
  margin-top: 40px;
}

.add-comment-title {
  margin-bottom: 8px;
}

.add-comment {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 32px;
}

.comments-list {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.comment {
  display: flex;
  flex-direction: column;
}

textarea {
  width: 100%;
  height: 80px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

.add-btn {
  align-self: flex-end;
  padding: 8px 16px;
  background-color: var(--secondary);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-btn:hover {
  outline: 2px solid var(--secondary-light);
}

.comment-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2px;
  border-bottom: 1px solid var(--gray-light);
}

.edit-wrapper{
  margin-top: 16px;
  margin-bottom: 36px;
}

.first-comment {
  border-top: 1px solid var(--gray-light);
}

.comment-content-wrapper {
}

.comment-userId {
  font-size: 14px;
  font-weight: bold;
}

.comment-content {
  margin-top: 12px;
}

.comment-time {
  margin-top: 4px;
  font-size: 14px;
  color: var(--gray-medium);
}

.btn-wrapper {
  position: relative;
}

.comment-btn {
  align-self: flex-end;
  padding: 4px 8px;
  margin-left: 4px;
  background-color: var(--gray-medium);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-btn:hover {
  outline: 2px solid var(--gray-light);
}

.add-btn {
  align-self: flex-end;
  padding: 8px 16px;
  background-color: var(--secondary);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-btn:hover {
  outline: 2px solid var(--secondary-light);
}

.menu-btn{
  background-color: inherit;
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 100%;
  cursor: pointer;
}

.menu-btn:hover{
  background-color: var(--gray-light);
}

.menu-dropdown {
  position: absolute;
  right: 0;
  top: 100%;
  width: 100px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.dropdown-btn {
  display: block;
  padding: 8px 16px;
  width: 100%;
  background-color: var(--white);
  border: none;
  text-align: left;
  cursor: pointer;
}

.dropdown-btn:hover {
  background-color: var(--secondary-light);
}

/* 768px 이상에서 */
@media (min-width: 768px) {
  .container {
    padding: 60px 120px;
  }
}

/* 1200px 이상에서 */
@media (min-width: 1200px) {
  .container {
    padding: 60px 120px;
    flex-direction: column;
    align-items: center;
  }

  .detail-wrapper {
    /* max-width: 80%; */
    width: 1024px;
  }

  .detail-contents,
  .detail-imgs,
  .comments-section
   {
    width: 80%;
  }
}
</style>
