<script setup>
import { ref } from 'vue'
import AppNav from './components/AppNav.vue'
import UserStatus from './components/UserStatus.vue'
import AuthPage from './pages/AuthPage.vue'
import CreatePostPage from './pages/CreatePostPage.vue'
import PostListPage from './pages/PostListPage.vue'

const STORAGE_KEY = 'social-media-user'

const currentPage = ref('posts')
const user = ref(loadUser())
const message = ref('')
const messageType = ref('info')
const postListRef = ref(null)

function loadUser() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY))
  } catch {
    return null
  }
}

function setMessage(text, type = 'info') {
  message.value = text
  messageType.value = type
}

function clearMessage() {
  message.value = ''
}

function changePage(page) {
  currentPage.value = page
  clearMessage()
}

function handleLogin(nextUser) {
  user.value = nextUser
  localStorage.setItem(STORAGE_KEY, JSON.stringify(nextUser))
  currentPage.value = 'posts'
}

function logout() {
  user.value = null
  localStorage.removeItem(STORAGE_KEY)
  setMessage('已登出', 'secondary')
}

function requireLogin() {
  currentPage.value = 'register'
  setMessage('請先登入後再操作', 'warning')
}

async function handlePostCreated() {
  currentPage.value = 'posts'
  await postListRef.value?.fetchPosts()
}
</script>

<template>
  <div class="bg-light min-vh-100">
    <AppNav :current-page="currentPage" :user="user" @change-page="changePage" />

    <main class="container py-4">
      <UserStatus :user="user" @logout="logout" />

      <div v-if="message" class="alert" :class="`alert-${messageType}`">{{ message }}</div>

      <AuthPage
        v-if="currentPage === 'register'"
        :user="user"
        @login-success="handleLogin"
        @logout="logout"
        @message="setMessage"
      />

      <PostListPage
        v-if="currentPage === 'posts'"
        ref="postListRef"
        :user="user"
        @need-login="requireLogin"
        @message="setMessage"
      />

      <CreatePostPage
        v-if="currentPage === 'create'"
        :user="user"
        @created="handlePostCreated"
        @need-login="requireLogin"
        @message="setMessage"
      />
    </main>
  </div>
</template>
