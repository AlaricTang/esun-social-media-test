<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  addComment,
  deletePost,
  getAllPosts,
  getCommentsByPostId,
  updatePost
} from '../api/socialMediaApi'
import { formatDate, getErrorMessage, normalizePosts } from '../utils/format'

const props = defineProps({
  user: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['need-login', 'message'])

const posts = ref([])
const loading = ref(false)
const commentsByPostId = reactive({})
const commentForms = reactive({})
const visibleComments = reactive({})
const editingPostId = ref(null)
const editForm = reactive({ content: '', image: '' })

function canManagePost(post) {
  return props.user && props.user.userId === post.userId
}

async function fetchPosts() {
  loading.value = true
  try {
    const res = await getAllPosts()
    posts.value = normalizePosts(res.data)
  } catch (error) {
    emit('message', getErrorMessage(error, '取得發文失敗'), 'danger')
  } finally {
    loading.value = false
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

async function submitUpdate(postId) {
  if (!editForm.content.trim()) {
    emit('message', '請輸入發文內容', 'warning')
    return
  }

  try {
    await updatePost(postId, {
      userId: props.user.userId,
      content: editForm.content.trim(),
      image: editForm.image.trim()
    })
    cancelEdit()
    await fetchPosts()
    emit('message', '編輯成功', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '編輯發文失敗'), 'danger')
  }
}

async function submitDelete(postId) {
  if (!window.confirm('確定要刪除這篇發文嗎？')) return

  try {
    await deletePost(postId, props.user.userId)
    delete commentsByPostId[postId]
    delete visibleComments[postId]
    await fetchPosts()
    emit('message', '刪除成功', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '刪除發文失敗'), 'danger')
  }
}

async function toggleComments(postId) {
  visibleComments[postId] = !visibleComments[postId]
  if (visibleComments[postId] && !commentsByPostId[postId]) {
    await fetchComments(postId)
  }
}

async function fetchComments(postId) {
  try {
    const res = await getCommentsByPostId(postId)
    commentsByPostId[postId] = res.data?.commentsList || []
  } catch (error) {
    commentsByPostId[postId] = []
    emit('message', getErrorMessage(error, '取得留言失敗'), 'danger')
  }
}

async function submitComment(postId) {
  if (!props.user) {
    emit('need-login')
    return
  }

  const content = (commentForms[postId] || '').trim()
  if (!content) {
    emit('message', '請輸入留言內容', 'warning')
    return
  }

  try {
    await addComment({
      userId: props.user.userId,
      postId,
      content
    })
    commentForms[postId] = ''
    visibleComments[postId] = true
    await fetchComments(postId)
    emit('message', '留言成功', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '新增留言失敗'), 'danger')
  }
}

onMounted(fetchPosts)

defineExpose({ fetchPosts })
</script>

<template>
  <section>
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div>
        <h2 class="h4 mb-1">瀏覽所有發文</h2>
        <div class="text-muted">{{ loading ? '載入中...' : `${posts.length} 則發文` }}</div>
      </div>
      <button class="btn btn-outline-secondary" @click="fetchPosts">重新整理</button>
    </div>

    <div v-if="!loading && posts.length === 0" class="alert alert-secondary">目前沒有任何發文。</div>

    <article v-for="post in posts" :key="post.postId" class="card mb-3">
      <div class="card-body">
        <div class="d-flex justify-content-between gap-3 mb-3">
          <div>
            <h3 class="h6 mb-1">{{ post.userName || '未知使用者' }}</h3>
            <div class="text-muted small">{{ formatDate(post.createdAt) }}</div>
          </div>
          <div v-if="canManagePost(post)" class="d-flex gap-2">
            <button class="btn btn-sm btn-outline-secondary" @click="startEdit(post)">編輯</button>
            <button class="btn btn-sm btn-outline-danger" @click="submitDelete(post.postId)">刪除</button>
          </div>
        </div>

        <form v-if="editingPostId === post.postId" @submit.prevent="submitUpdate(post.postId)">
          <div class="mb-3">
            <label class="form-label">發文內容</label>
            <textarea v-model="editForm.content" class="form-control" rows="4"></textarea>
          </div>
          <div class="mb-3">
            <label class="form-label">圖片網址</label>
            <input v-model="editForm.image" class="form-control" type="url" />
          </div>
          <div class="d-flex gap-2">
            <button class="btn btn-primary btn-sm" type="submit">儲存</button>
            <button class="btn btn-outline-secondary btn-sm" type="button" @click="cancelEdit">取消</button>
          </div>
        </form>

        <template v-else>
          <p class="card-text post-content">{{ post.content }}</p>
          <img v-if="post.image" :src="post.image" class="img-fluid rounded border mb-3" alt="貼文圖片" />
        </template>

        <div class="mt-3">
          <button class="btn btn-sm btn-outline-primary" @click="toggleComments(post.postId)">
            {{ visibleComments[post.postId] ? '收合留言' : '查看留言' }}
          </button>
        </div>
      </div>

      <div v-if="visibleComments[post.postId]" class="card-footer bg-body-tertiary">
        <h4 class="h6">留言</h4>
        <div v-if="!commentsByPostId[post.postId]?.length" class="text-muted mb-3">目前沒有留言。</div>
        <div v-for="comment in commentsByPostId[post.postId]" :key="comment.commentId" class="border rounded p-2 mb-2 bg-white">
          <div class="fw-semibold small">{{ comment.userName || '未知使用者' }}</div>
          <div>{{ comment.content }}</div>
          <small class="text-muted">{{ formatDate(comment.createdAt) }}</small>
        </div>

        <form class="input-group mt-3" @submit.prevent="submitComment(post.postId)">
          <input v-model="commentForms[post.postId]" class="form-control" type="text" placeholder="輸入留言" />
          <button class="btn btn-primary" type="submit">留言</button>
        </form>
      </div>
    </article>
  </section>
</template>

<style scoped>
.post-content {
  white-space: pre-wrap;
  overflow-wrap: anywhere;
}
</style>
