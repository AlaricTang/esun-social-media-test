<script setup>
import { reactive } from 'vue'
import { createPost } from '../api/socialMediaApi'
import { getErrorMessage } from '../utils/format'

const props = defineProps({
  user: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['created', 'need-login', 'message'])

const postForm = reactive({ content: '', image: '' })

async function submitPost() {
  if (!props.user) {
    emit('need-login')
    return
  }
  if (!postForm.content.trim()) {
    emit('message', '請輸入發文內容', 'warning')
    return
  }

  try {
    await createPost({
      userId: props.user.userId,
      content: postForm.content.trim(),
      image: postForm.image.trim()
    })
    postForm.content = ''
    postForm.image = ''
    emit('created')
    emit('message', '發文成功', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '新增發文失敗'), 'danger')
  }
}
</script>

<template>
  <section class="card">
    <div class="card-header bg-white">
      <h2 class="h5 mb-0">新增發文</h2>
    </div>
    <form class="card-body" @submit.prevent="submitPost">
      <div v-if="!user" class="alert alert-warning">請先登入後才能新增發文。</div>
      <div class="mb-3">
        <label class="form-label">發文內容</label>
        <textarea v-model="postForm.content" class="form-control" :disabled="!user" rows="5" placeholder="輸入發文內容"></textarea>
      </div>
      <div class="mb-3">
        <label class="form-label">圖片網址，可不填</label>
        <input v-model="postForm.image" class="form-control" :disabled="!user" type="url" placeholder="https://..." />
      </div>
      <button class="btn btn-primary" type="submit" :disabled="!user">發布</button>
    </form>
  </section>
</template>
