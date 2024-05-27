<script setup>
import { ref, reactive, onMounted, onBeforeUnmount } from "vue";
import { useRoute } from "vue-router";
import VerticalHeader from "@/components/common/VerticalHeader.vue";
import LoadingSpinner from "@/components/search/LoadingSpinner.vue";
import KakaoMap from "@/components/search/KakaoMap.vue";
import VSelect from "@/components/search/VSelect.vue";
import VCard from "@/components/search/VCard.vue";

//api
import { localAxios, localAuthAxios } from "@/utils/http-commons";
const local = localAxios();
const localAuth = localAuthAxios();

// 스토어 가져오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userId } = storeToRefs(memberStore);

const route = useRoute();
const selectedItem = route.query.selectedItem;

//메뉴
const toggleMenu = (index) => {
  selectedCard.memos[index].showMenu = !selectedCard.memos[index].showMenu;
};

//gpt
const gptContent = ref("");
const isGptLoading = ref(false);
const getGPT = async (title) => {
  isGptLoading.value = true;
  const response = await localAuth.get(`/bot/chat?prompt=${title}`);
  console.log("===>getGPT: ", response.data);
  gptContent.value = response.data;
  console.log(gptContent);
  isGptLoading.value = false;
};
function formatText(text) {
  // "2."와 "3." 앞에 줄 바꿈 추가
  return text.replace(/2\./, "\n\n2.").replace(/3\./, "\n\n3.");
}

// Modal
const showModal = ref(false);
const selectedCard = reactive({
  id: "",
  pid: "",
  title: "",
  overview: "",
  memo: "",
  memos: [], // array to hold multiple memos
  otherMemos: [],
  editIndex: null, // index for the memo being edited
});

const place = ref({});
const openModal = async (card) => {
  console.log("openModal==>card: ", card);
  selectedCard.id = card.content_id;
  selectedCard.pid = card.pid;
  selectedCard.title = card.title;
  selectedCard.overview = card.overview;
  selectedCard.title = card.title;
  selectedCard.memos = card.memos || []; // use existing memos or initialize an empty array
  selectedCard.otherMemos = card.otherComments;
  getComment();
  getGPT(card.title);
  place.value = card;
  showModal.value = true;
};

const closeModal = () => {
  selectedCard.title = "";
  selectedCard.memo = "";
  selectedCard.editIndex = null;
  showModal.value = false;
};

//하트(클릭)
const updatePlacePid = (id, pid) => {
  const place = filteredPlaces.value.find((place) => place.content_id === id);
  if (place) {
    place.pid = pid;
  }
};

const postPlan = async () => {
  const response = await localAuth.post(`/plan/${selectedCard.id}`, {
    userId: userId.value,
    contentId: selectedCard.id,
  });
  console.log("===>postPlan: ", response.data);
  selectedCard.pid = response.data;
  updatePlacePid(selectedCard.id, response.data);
  getComment();
};

const deletePlan = async () => {
  const response = await localAuth.delete(`/plan/${selectedCard.pid}`);
  console.log("===>deletePlan: ", response.data);
  updatePlacePid(selectedCard.id, 0);
  selectedCard.pid = 0;
};

//memo
const isPublic = ref(true);

const getComment = async () => {
  const response = await localAuth.post(`/plan/search`, {
    pid: selectedCard.pid,
    contentId: selectedCard.id,
    userId: userId.value,
  });
  console.log("===>getComment: ", response.data);
  selectedCard.memos = response.data.myComment;
  selectedCard.otherMemos = response.data.otherComment;
};

const modifyComment = async (memo) => {
  console.log(memo);
  console.log(selectedCard.memo);
  const response = await localAuth.patch(`/plan/comment`, {
    cid: memo.cid,
    pid: memo.pid,
    userId: userId.value,
    content: selectedCard.memo,
    isShow: isPublic.value,
  });
  console.log("===>modifyComment: ", response.data);
  //selectedCard.memos = response.data.myComment;
  getComment();
};

const deleteComment = async (cid) => {
  const response = await localAuth.delete(`/plan/comment/${cid}`);
  console.log("===>deleteComment: ", response.data);
  //selectedCard.memos = response.data.myComment;
  getComment();
};

const postMemo = async (content, isShow) => {
  const response = await localAuth.post(`/plan/comment`, {
    pid: selectedCard.pid,
    userId: userId.value,
    content: content,
    isShow: isShow,
    contentId: selectedCard.id,
  });
  getComment();
  console.log("===>postMemo: ", response.data);
};

const saveMemo = () => {
  if (selectedCard.memo.trim()) {
    if (selectedCard.editIndex !== null) {
      // Editing an existing memo
      selectedCard.memos[selectedCard.editIndex] = selectedCard.memo;
      selectedCard.editIndex = null;
    } else {
      // Adding a new memo
      postMemo(selectedCard.memo, isPublic.value);
      selectedCard.memos.push(selectedCard.memo);
    }
    selectedCard.memo = "";
    console.log("메모 저장: ", selectedCard.memos);
  }
};

const editingMemo = ref();

const startEdit = (index, memo) => {
  selectedCard.memo = selectedCard.memos[index].content;
  selectedCard.editIndex = index;
  isPublic.value = memo.show;
  editingMemo.value = memo;
  console.log("===>startEdit===>memo: ", memo);
};

const editMemo = () => {
  console.log("editMemo: ", editingMemo.value);
  modifyComment(editingMemo.value);
  selectedCard.memo = "";
  selectedCard.editIndex = null;
  getComment();
};

const deleteMemo = (index, cid) => {
  console.log("===>delteMemo===>cid: ", cid);
  deleteComment(cid);
  // selectedCard.memos.splice(index, 1);
  // selectedCard.editIndex = null;
  // console.log("메모 삭제");
};

// Search panel
const showSearchPanel = ref(true);
const toggleSearchPanel = () => {
  showSearchPanel.value = !showSearchPanel.value;
};

// Option
const options = ref([
  { id: "0", name: "전체" },
  { id: "12", name: "관광지" },
  { id: "14", name: "문화시설" },
  { id: "15", name: "축제공연행사" },
  { id: "25", name: "여행코스" },
  { id: "28", name: "레포츠" },
  { id: "32", name: "숙박" },
  { id: "38", name: "쇼핑" },
  { id: "39", name: "음식점" },
]);
const getIdByName = (name) => {
  const option = options.value.find((option) => option.name === name);
  return option ? option.id : null;
};
const selectedOption = ref("전체");
const setSelectedOption = (option) => {
  console.log("option 변경: ", option);
  selectedOption.value = option;
};

// 검색
const places = ref([]);
const filteredPlaces = ref([]); // to hold the places currently being rendered
const keyword = ref("");
let page = 1;
const loading = ref(false);
let observer = null;
const showObserver = ref(false);
const itemsPerPage = 20; // Number of items to show per page

const search = async (reset = true) => {
  if (reset) {
    page = 1;
    places.value = [];
    filteredPlaces.value = [];
  }
  loading.value = true;
  try {
    closeModal();
    const response = await localAuth.get(
      `/place?keyword=${keyword.value}&contentTypeId=${getIdByName(selectedOption.value)}`
    );
    console.log("===>search: ", response.data.places);
    places.value = response.data.places;
    place.value = response.data.places[0];
    updateFilteredPlaces();
    return response.data.places;
  } catch (error) {
    console.log(error);
  } finally {
    loading.value = false;
  }
  console.log(filteredPlaces.value);
};

const updateFilteredPlaces = () => {
  const start = (page - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  filteredPlaces.value = [...filteredPlaces.value, ...places.value.slice(start, end)];
};

const handleObserver = (entries) => {
  if (loading.value) return; //로딩중인경우
  if (places.value.length === 0) return; //아직 검색하지 않았을 경우
  if (places.value.length === filteredPlaces.value.length) return; //끝
  console.log("추가스크롤:", page);
  entries.forEach((entry) => {
    if (entry.isIntersecting && !loading.value) {
      loadMore();
    }
  });
  loadMore();
};

const loadMore = () => {
  loading.value = true;
  page += 1;
  updateFilteredPlaces();
  loading.value = false;
};

const auth = ref(false);
onMounted(() => {
  console.log("mounted!!!!!");
  auth.value = !(userId?.value === null);
  observer = new IntersectionObserver(handleObserver, {
    threshold: 0.5,
  });
  const searchObserve = document.querySelector(".searchObserve");
  observer.observe(searchObserve);
  if (selectedItem) {
    keyword.value = selectedItem;
    selectedCard.title = selectedItem;
    showModal.value = true;
  } else {
    keyword.value = route.query.keyword;
  }
  if (keyword.value) {
    console.log("검색어:", keyword.value);
    search().then((res) => {
      console.log(res);
      openModal(res[0]);
    });
  } else {
    console.log("검색어:", "현재 검색어 없음");
  }
});

onBeforeUnmount(() => {
  observer?.disconnect();
});
</script>

<template>
  <VerticalHeader />
  <main>
    <section class="map" id="map">
      <KakaoMap :places="filteredPlaces" :place="place" />
    </section>
    <section :class="['search-panel', { close: !showSearchPanel }]">
      <form class="search-form" @submit.prevent="search">
        <VSelect
          :selectedOption="selectedOption"
          :options="options"
          @setSelectedOption="setSelectedOption"
        ></VSelect>
        <input
          v-model="keyword"
          type="text"
          class="search-form__input"
          placeholder="장소를 입력하세요"
          @keyup.enter="search"
        />
        <button @click="search" class="search-form__button-search" type="button">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
        </button>
      </form>
      <div class="search-result" ref="searchResult">
        <VCard
          v-for="place in filteredPlaces"
          :place="place"
          :key="place.contentid"
          @click="openModal(place)"
          :defaultImage="'/path/to/default/image.jpg'"
        ></VCard>
        <div v-if="loading" class="loading-indicator">
          <LoadingSpinner />
        </div>
        <div class="searchObserve"></div>
      </div>
      <button @click="toggleSearchPanel" class="search-panel__button-panel-open">
        <font-awesome-icon :icon="['fas', showSearchPanel ? 'angle-left' : 'angle-right']" />
      </button>
    </section>
    <section class="modal-panel" v-if="showModal" @close="closeModal">
      <div class="modal-header">
        <button class="modal-close-btn" type="button" @click="closeModal">
          <font-awesome-icon :icon="['fas', 'x']" />
        </button>
      </div>
      <div class="modal-body">
        <div class="modal-title-wrapper">
          <h3 class="modal-title">{{ selectedCard.title }}</h3>
          <button
            @click="postPlan"
            class="search-result__card-button-heart"
            v-if="selectedCard.pid === 0 && auth"
          >
            <font-awesome-icon :icon="['far', 'heart']" />
          </button>
          <button
            @click="deletePlan"
            class="search-result__card-button-heart"
            v-if="selectedCard.pid > 0 && auth"
          >
            <font-awesome-icon :icon="['fas', 'heart']" />
          </button>
        </div>
        <p class="modal-overview">
          {{
            selectedCard?.overview.length > 100
              ? selectedCard?.overview.slice(0, 100) + "..."
              : selectedCard?.overview
          }}
        </p>

        <!-- chat gpt start -->
        <div class="gpt-wrapper">
          <p class="gpt-title">👀 AI가 알려주는 추천일정!</p>
          <div v-if="isGptLoading" class="gpt-loading"><LoadingSpinner /></div>
          <div v-else class="gpt-content">
            <pre class="gpt-content-pre">{{ gptContent }}</pre>
          </div>
        </div>
        <!-- chat gpt end -->

        <div v-if="selectedCard.pid > 0">
          <textarea
            class="modal-textarea"
            placeholder="메모를 입력하세요"
            v-model="selectedCard.memo"
          ></textarea>
          <div class="radio-wrapper">
            <label> <input type="radio" v-model="isPublic" :value="true" /> 공개 </label>
            <label> <input type="radio" v-model="isPublic" :value="false" /> 비공개 </label>
          </div>
          <div>
            <button
              class="modal-save-btn"
              type="button"
              v-if="selectedCard.editIndex !== null"
              @click="editMemo"
            >
              {{ selectedCard.editIndex !== null ? "수정" : "저장" }}
            </button>
            <button class="modal-save-btn" type="button" v-else @click="saveMemo">
              {{ selectedCard.editIndex !== null ? "수정" : "저장" }}
            </button>
          </div>
          <div v-if="selectedCard.memos.length" class="memos">
            <div
              v-for="(memo, index) in selectedCard.memos"
              :key="index"
              :class="['memo-item', { 'first-memo': index === 0 }]"
            >
              <p>{{ memo.content }}</p>
              <div class="memo-menu-btn-wrapper">
                <button class="memo-menu-btn" @click="toggleMenu(index)">
                  <font-awesome-icon :icon="['fas', 'bars']" />
                </button>
                <div v-if="memo.showMenu" class="memo-menu">
                  <button @click="startEdit(index, memo)">수정</button>
                  <button @click="deleteMemo(index, memo.cid)">삭제</button>
                </div>
              </div>
            </div>
          </div>
          <div class="otherMemos">
            <p class="otherMemos-title">📑 공개 메모</p>
            <div
              v-for="(memo, index) in selectedCard.otherMemos"
              :key="index"
              :class="['memo-item', { 'first-memo': index === 0 }]"
            >
              <p>{{ memo.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
}

main {
  position: relative;
  display: flex;
  justify-content: stretch;
  align-items: stretch;
  height: calc(100vh - 80px);
}

.search-panel {
  position: absolute;
  top: 0px;
  left: 80px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 390px;
  height: 100vh;
  background-color: var(--white);
  transition: transform 0.5s ease-in-out;
  transform: translateX(0%);
  z-index: 1;
}

.search-panel.close {
  transform: translateX(-100%);
}

.search-form {
  display: flex;
  margin-top: 36px;
  width: 340px;
  height: 48px;
  outline: 2px solid var(--primary);
  border-radius: 8px;
}

.search-form__input {
  padding: 0px 8px;
  width: calc(100% - 100px - 40px);
  font-size: 16px;
  border: none;
}

.search-form__button-search {
  padding: 12px;
  width: 40px;
  height: 100%;
  font-size: 16px;
  background-color: var(--white);
  outline: none;
  border: none;
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
  cursor: pointer;
}

.search-result {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  margin-top: 36px;
  width: 100%;
  height: calc(100% - 36px - 48px - 24px);
  overflow-y: auto;
}

.search-panel__button-panel-open {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: calc(50% - 14px);
  right: -20px;
  padding: 24px 8px;
  width: 20px;
  height: 28px;
  font-size: 16px;
  color: var(--gray-800);
  background-color: var(--white);
  border: none;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  cursor: pointer;
}

.map {
  position: absolute;
  top: 0;
  left: 0;
  background-color: var(--gray-light);
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.loading-indicator {
  margin-top: 32px;
  margin-bottom: 60px;
}

.modal-panel {
  position: absolute;
  top: 0px;
  right: 0;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 390px;
  height: 100vh;
  background-color: var(--white);
  overflow: auto;
  z-index: 1;
}

.modal-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
  width: 100%;
}

.modal-close-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  border: none;
  border-radius: 100%;
  background-color: inherit;
  cursor: pointer;
}

.modal-close-btn:hover {
  background-color: var(--secondary-light);
}

.modal-body {
  display: flex;
  flex-direction: column;
  padding: 32px;
  width: 100%;
}

.modal-title-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.modal-overview {
  margin-bottom: 16px;
  color: var(--gray-dark);
  font-size: 14px;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
  border-top: 1px solid #ccc;
  background-color: antiquewhite;
}

.modal-textarea {
  width: 100%;
  min-height: 100px;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  resize: vertical;
}

.radio-wrapper {
  margin-top: 4px;
  font-size: 14px;
}

.modal-save-btn {
  margin-top: 12px;
  margin-right: 8px;
  width: 64px;
  height: 32px;
  border: none;
  border-radius: 10px;
  background-color: var(--secondary);
  color: var(--white);
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.modal-cancel-btn {
  margin-top: 8px;
  margin-right: 8px;
  width: 64px;
  height: 32px;
  border: none;
  border-radius: 10px;
  background-color: var(--gray-medium);
  color: var (--white);
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.modal-save-btn:hover {
  outline: 4px solid var(--secondary-light);
}

.modal-cancel-btn:hover {
  outline: 4px solid var(--gray-light);
}

.memos {
  margin-top: 16px;
}

.memo-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #ccc;
}

.memo-update-btn,
.memo-delete-btn {
  margin-left: 8px;
  padding: 4px 8px;
  border: none;
  border-radius: 4px;
  background-color: var(--secondary);
  color: var(--white);
  cursor: pointer;
}

.memo-delete-btn {
  background-color: var(--gray-medium);
}

.memo-update-btn:hover {
  outline: 2px solid var(--secondary-light);
}

.memo-delete-btn:hover {
  outline: 2px solid var(--gray-light);
}

.search-result__card-button-heart {
  font-size: 18px;
  color: var(--red-medium);
  background-color: inherit;
  border: none;
  cursor: pointer;
}

.memo-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #ccc;
  font-size: 14px;
}

.memo-item.first-memo {
  border-top: 1px solid #ccc;
}

.memo-menu-btn-wrapper {
  position: relative;
}

.memo-menu-btn {
  background-color: inherit;
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 100%;
  cursor: pointer;
}

.memo-menu-btn:hover {
  background-color: var(--gray-light);
}

.memo-menu {
  position: absolute;
  right: 0;
  top: 100%;
  width: 80px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.memo-menu button {
  display: block;
  padding: 8px 16px;
  width: 100%;
  background-color: var(--white);
  border: none;
  text-align: left;
  cursor: pointer;
}

.memo-menu button:hover {
  background-color: var(--secondary-light);
}

.otherMemos {
  margin-top: 32px;
}

.otherMemos-title {
  margin-top: 16px;
  margin-bottom: 16px;
  font-size: 16px;
  font-weight: bold;
}

.gpt-wrapper {
  margin-bottom: 16px;
}

.gpt-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
}

.gpt-loading {
  margin-top: 16px;
}

.gpt-content {
  font-size: 14px;
}

.gpt-content-pre {
  white-space: pre-wrap; /* CSS3 */
  white-space: -moz-pre-wrap; /* Mozilla */
  white-space: -pre-wrap; /* Opera 4-6 */
  white-space: -o-pre-wrap; /* Opera 7 */
  word-wrap: break-word; /* IE 5.5+ */
  max-width: 100%; /* Ensure it doesn't go beyond the container's width */
  border: 1px solid #ccc; /* Optional: for better visualization */
  padding: 10px; /* Optional: for better visualization */
}
</style>
