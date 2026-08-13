<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import axios from 'axios'

const STORAGE_KEY = 'social-media-user'

const currentPage = ref('posts')
const user = ref(loadUser())
const posts = ref([])
const commentsByPostId = reactive({})
const commentForms = reactive({})
const visibleComments = reactive({})
const editingPostId = ref(null)
const editForm = reactive({ content: '', image: '' })
const loading = ref(false)
const message = ref('')
const messageType = ref('info')

const registerForm = reactive({ mobile: '', userName: '', email: '', password: '' })
const loginForm = reactive({ mobile: '', password: '' })
const postForm = reactive({ content: '', image: '' })

const isLoggedIn = computed(() => Boolean(user.value?.userId))

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

function clearLoginForm() {
  loginForm.mobile = ''
  loginForm.password = ''
}

function clearRegisterForm() {
  registerForm.mobile = ''
  registerForm.userName = ''
  registerForm.email = ''
  registerForm.password = ''
}

function setUser(nextUser) {
  user.value = nextUser
  localStorage.setItem(STORAGE_KEY, JSON.stringify(nextUser))
}

function logout() {
  user.value = null
  localStorage.removeItem(STORAGE_KEY)
  cancelEdit()
  clearLoginForm()
  setMessage('已登出', 'secondary')
}

function showPage(page) {
  currentPage.value = page
  clearMessage()
  cancelEdit()
  if (page === 'posts') fetchPosts()
}

function formatDate(value) {
  if (!value) return ''
  return new Date(value).toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function getErrorMessage(error, fallback) {
  return error?.response?.data?.message || fallback
}

function normalizePosts(data) {
  const list = data?.AllPostsList || data?.allPostsList || []
  return Array.isArray(list) ? list : []
}

function canManagePost(post) {
  return isLoggedIn.value && user.value.userId === post.userId
}

async function register() {
  clearMessage()
  try {
    await axios.post('/api/user/users', {
      mobile: registerForm.mobile,
      userName: registerForm.userName,
      email: registerForm.email,
      password: registerForm.password
    })
    loginForm.mobile = registerForm.mobile
    clearRegisterForm()
    setMessage('註冊成功，請使用手機號碼登入', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '註冊失敗'), 'danger')
  }
}

async function login() {
  if (isLoggedIn.value) {
    setMessage('目前已登入，請先登出再登入其他帳號', 'warning')
    return
  }

  clearMessage()
  try {
    const res = await axios.post('/api/user/login', {
      mobile: loginForm.mobile,
      password: loginForm.password
    })
    setUser(res.data)
    clearLoginForm()
    currentPage.value = 'posts'
    await fetchPosts()
    setMessage('登入成功', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '登入失敗'), 'danger')
  }
}

async function fetchPosts() {
  loading.value = true
  try {
    const res = await axios.get('/api/post/getAllPosts')
    posts.value = normalizePosts(res.data)
  } catch (error) {
    setMessage(getErrorMessage(error, '取得發文失敗'), 'danger')
  } finally {
    loading.value = false
  }
}

async function createPost() {
  if (!isLoggedIn.value) {
    setMessage('請先登入才能新增發文', 'warning')
    currentPage.value = 'register'
    return
  }
  if (!postForm.content.trim()) {
    setMessage('請輸入發文內容', 'warning')
    return
  }

  try {
    await axios.post('/api/post/createPost', {
      userId: user.value.userId,
      content: postForm.content.trim(),
      image: postForm.image.trim()
    })
    postForm.content = ''
    postForm.image = ''
    currentPage.value = 'posts'
    await fetchPosts()
    setMessage('發文成功', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '新增發文失敗'), 'danger')
  }
}

function startEdit(post) {
  editingPostId.value = post.postId
  editForm.content = post.content || ''
  editForm.image = post.image || ''
}

function cancelEdit() {
  editingPostId.value = null
  editForm.content = ''
  editForm.image = ''
}

async function updatePost(postId) {
  if (!editForm.content.trim()) {
    setMessage('請輸入發文內容', 'warning')
    return
  }

  try {
    await axios.put(`/api/post/posts/${postId}`, {
      userId: user.value.userId,
      content: editForm.content.trim(),
      image: editForm.image.trim()
    })
    cancelEdit()
    await fetchPosts()
    setMessage('編輯成功', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '編輯發文失敗'), 'danger')
  }
}

async function deletePost(postId) {
  if (!window.confirm('確定要刪除這篇發文嗎？')) return

  try {
    await axios.delete(`/api/post/posts/${postId}/users/${user.value.userId}`)
    delete commentsByPostId[postId]
    delete visibleComments[postId]
    await fetchPosts()
    setMessage('刪除成功', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '刪除發文失敗'), 'danger')
  }
}

async function toggleComments(postId) {
  visibleComments[postId] = !visibleComments[postId]
  if (visibleComments[postId] && !commentsByPostId[postId]) await fetchComments(postId)
}

async function fetchComments(postId) {
  try {
    const res = await axios.get(`/api/comment/posts/${postId}/comments`)
    commentsByPostId[postId] = res.data?.commentsList || []
  } catch (error) {
    commentsByPostId[postId] = []
    setMessage(getErrorMessage(error, '取得留言失敗'), 'danger')
  }
}

async function addComment(postId) {
  if (!isLoggedIn.value) {
    setMessage('請先登入才能留言', 'warning')
    currentPage.value = 'register'
    return
  }

  const content = (commentForms[postId] || '').trim()
  if (!content) {
    setMessage('請輸入留言內容', 'warning')
    return
  }

  try {
    await axios.post('/api/comment/comments', {
      userId: user.value.userId,
      postId,
      content
    })
    commentForms[postId] = ''
    visibleComments[postId] = true
    await fetchComments(postId)
    setMessage('留言成功', 'success')
  } catch (error) {
    setMessage(getErrorMessage(error, '新增留言失敗'), 'danger')
  }
}

onMounted(fetchPosts)
</script>

<template>
  <div class="bg-light min-vh-100">
    <nav class="navbar navbar-expand-lg bg-white border-bottom sticky-top">
      <div class="container">
        <button class="navbar-brand btn btn-link p-0 text-decoration-none fw-bold text-dark" @click="showPage('posts')">
          社群媒體平台
        </button>
        <div class="d-flex gap-2 flex-wrap">
          <button class="btn" :class="currentPage === 'register' ? 'btn-primary' : 'btn-outline-primary'" @click="showPage('register')">註冊 / 登入</button>
          <button class="btn" :class="currentPage === 'posts' ? 'btn-primary' : 'btn-outline-primary'" @click="showPage('posts')">瀏覽發文</button>
          <button class="btn" :class="currentPage === 'create' ? 'btn-primary' : 'btn-outline-primary'" @click="showPage('create')">新增發文</button>
        </div>
      </div>
    </nav>

    <main class="container py-4">
      <div class="card mb-3">
        <div class="card-body d-flex justify-content-between align-items-center flex-wrap gap-2">
          <div>
            <div class="fw-semibold">{{ isLoggedIn ? `目前登入：${user.userName}` : '尚未登入' }}</div>
            <div class="text-muted small">{{ isLoggedIn ? user.mobile : '登入後可以發文、留言、管理自己的發文' }}</div>
          </div>
          <button v-if="isLoggedIn" class="btn btn-outline-secondary btn-sm" @click="logout">登出</button>
        </div>
      </div>

      <div v-if="message" class="alert" :class="`alert-${messageType}`">{{ message }}</div>

      <section v-if="currentPage === 'register'" class="row g-4">
        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header bg-white"><h2 class="h5 mb-0">註冊</h2></div>
            <form class="card-body" @submit.prevent="register">
              <div class="mb-3"><label class="form-label">手機號碼</label><input v-model="registerForm.mobile" class="form-control" type="tel" placeholder="0912345678" /></div>
              <div class="mb-3"><label class="form-label">使用者名稱</label><input v-model="registerForm.userName" class="form-control" type="text" /></div>
              <div class="mb-3"><label class="form-label">Email</label><input v-model="registerForm.email" class="form-control" type="email" /></div>
              <div class="mb-3"><label class="form-label">密碼</label><input v-model="registerForm.password" class="form-control" type="password" /></div>
              <button class="btn btn-primary w-100" type="submit">註冊</button>
            </form>
          </div>
        </div>

        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header bg-white"><h2 class="h5 mb-0">登入</h2></div>
            <div v-if="isLoggedIn" class="card-body">
              <p class="mb-3">你已經登入為 {{ user.userName }}，若要登入其他帳號請先登出。</p>
              <button class="btn btn-outline-secondary" @click="logout">登出</button>
            </div>
            <form v-else class="card-body" @submit.prevent="login">
              <div class="mb-3"><label class="form-label">手機號碼</label><input v-model="loginForm.mobile" class="form-control" type="tel" placeholder="0912345678" /></div>
              <div class="mb-3"><label class="form-label">密碼</label><input v-model="loginForm.password" class="form-control" type="password" /></div>
              <button class="btn btn-success w-100" type="submit">登入</button>
            </form>
          </div>
        </div>
      </section>

      <section v-if="currentPage === 'posts'">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <div><h2 class="h4 mb-1">瀏覽所有發文</h2><div class="text-muted">{{ loading ? '載入中...' : `${posts.length} 則發文` }}</div></div>
          <button class="btn btn-outline-secondary" @click="fetchPosts">重新整理</button>
        </div>
        <div v-if="!loading && posts.length === 0" class="alert alert-secondary">目前沒有任何發文。</div>

        <article v-for="post in posts" :key="post.postId" class="card mb-3">
          <div class="card-body">
            <div class="d-flex justify-content-between gap-3 mb-3">
              <div><h3 class="h6 mb-1">{{ post.userName || '未知使用者' }}</h3><div class="text-muted small">{{ formatDate(post.createdAt) }}</div></div>
              <div v-if="canManagePost(post)" class="d-flex gap-2">
                <button class="btn btn-sm btn-outline-secondary" @click="startEdit(post)">編輯</button>
                <button class="btn btn-sm btn-outline-danger" @click="deletePost(post.postId)">刪除</button>
              </div>
            </div>

            <form v-if="editingPostId === post.postId" @submit.prevent="updatePost(post.postId)">
              <div class="mb-3"><label class="form-label">發文內容</label><textarea v-model="editForm.content" class="form-control" rows="4"></textarea></div>
              <div class="mb-3"><label class="form-label">圖片網址</label><input v-model="editForm.image" class="form-control" type="url" /></div>
              <div class="d-flex gap-2"><button class="btn btn-primary btn-sm" type="submit">儲存</button><button class="btn btn-outline-secondary btn-sm" type="button" @click="cancelEdit">取消</button></div>
            </form>

            <template v-else>
              <p class="card-text post-content">{{ post.content }}</p>
              <img v-if="post.image" :src="post.image" class="img-fluid rounded border mb-3" alt="貼文圖片" />
            </template>

            <div class="mt-3"><button class="btn btn-sm btn-outline-primary" @click="toggleComments(post.postId)">{{ visibleComments[post.postId] ? '收合留言' : '查看留言' }}</button></div>
          </div>

          <div v-if="visibleComments[post.postId]" class="card-footer bg-body-tertiary">
            <h4 class="h6">留言</h4>
            <div v-if="!commentsByPostId[post.postId]?.length" class="text-muted mb-3">目前沒有留言。</div>
            <div v-for="comment in commentsByPostId[post.postId]" :key="comment.commentId" class="border rounded p-2 mb-2 bg-white">
              <div class="fw-semibold small">{{ comment.userName || '未知使用者' }}</div>
              <div>{{ comment.content }}</div>
              <small class="text-muted">{{ formatDate(comment.createdAt) }}</small>
            </div>
            <form class="input-group mt-3" @submit.prevent="addComment(post.postId)">
              <input v-model="commentForms[post.postId]" class="form-control" type="text" placeholder="輸入留言" />
              <button class="btn btn-primary" type="submit">留言</button>
            </form>
          </div>
        </article>
      </section>

      <section v-if="currentPage === 'create'" class="card">
        <div class="card-header bg-white"><h2 class="h5 mb-0">新增發文</h2></div>
        <form class="card-body" @submit.prevent="createPost">
          <div v-if="!isLoggedIn" class="alert alert-warning">請先登入後才能新增發文。</div>
          <div class="mb-3"><label class="form-label">發文內容</label><textarea v-model="postForm.content" class="form-control" :disabled="!isLoggedIn" rows="5" placeholder="輸入發文內容"></textarea></div>
          <div class="mb-3"><label class="form-label">圖片網址，可不填</label><input v-model="postForm.image" class="form-control" :disabled="!isLoggedIn" type="url" placeholder="https://..." /></div>
          <button class="btn btn-primary" type="submit" :disabled="!isLoggedIn">發布</button>
        </form>
      </section>
    </main>
  </div>
</template>

<style scoped>
.post-content {
  white-space: pre-wrap;
  overflow-wrap: anywhere;
}
</style>
