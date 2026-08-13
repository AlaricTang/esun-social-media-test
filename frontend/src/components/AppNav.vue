<script setup>
defineProps({
  currentPage: {
    type: String,
    required: true
  },
  user: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['change-page'])
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-white border-bottom sticky-top">
    <div class="container">
      <button class="navbar-brand btn btn-link p-0 text-decoration-none fw-bold text-dark" @click="emit('change-page', 'posts')">
        社群媒體平台
      </button>
      <div class="d-flex gap-2 flex-wrap">
        <button
          v-if="!user"
          class="btn"
          :class="currentPage === 'register' ? 'btn-primary' : 'btn-outline-primary'"
          @click="emit('change-page', 'register')"
        >
          註冊 / 登入
        </button>
        <button
          class="btn"
          :class="currentPage === 'posts' ? 'btn-primary' : 'btn-outline-primary'"
          @click="emit('change-page', 'posts')"
        >
          瀏覽發文
        </button>
        <button
          class="btn"
          :class="currentPage === 'create' ? 'btn-primary' : 'btn-outline-primary'"
          @click="emit('change-page', 'create')"
        >
          新增發文
        </button>
      </div>
    </div>
  </nav>
</template>
