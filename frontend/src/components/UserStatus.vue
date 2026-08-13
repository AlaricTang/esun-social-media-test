<script setup>
defineProps({
  user: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['logout'])
</script>

<template>
  <div class="card mb-3">
    <div class="card-body d-flex justify-content-between align-items-center flex-wrap gap-2">
      <div>
        <div class="fw-semibold">{{ user ? `目前登入：${user.userName}` : '尚未登入' }}</div>
        <div class="text-muted small">{{ user ? user.mobile : '登入後可以發文、留言、管理自己的發文' }}</div>
      </div>
      <button v-if="user" class="btn btn-outline-secondary btn-sm" @click="emit('logout')">登出</button>
    </div>
  </div>
</template>
