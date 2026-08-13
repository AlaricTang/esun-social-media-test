<script setup>
import { reactive } from 'vue'
import { loginUser, registerUser } from '../api/socialMediaApi'
import { getErrorMessage } from '../utils/format'

defineProps({
  user: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['login-success', 'logout', 'message'])

const registerForm = reactive({ mobile: '', userName: '', email: '', password: '' })
const loginForm = reactive({ mobile: '', password: '' })

function clearRegisterForm() {
  registerForm.mobile = ''
  registerForm.userName = ''
  registerForm.email = ''
  registerForm.password = ''
}

function clearLoginForm() {
  loginForm.mobile = ''
  loginForm.password = ''
}

async function register() {
  try {
    await registerUser({
      mobile: registerForm.mobile,
      userName: registerForm.userName,
      email: registerForm.email,
      password: registerForm.password
    })
    loginForm.mobile = registerForm.mobile
    clearRegisterForm()
    emit('message', '註冊成功，請使用手機號碼登入', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '註冊失敗'), 'danger')
  }
}

async function login() {
  try {
    const res = await loginUser({
      mobile: loginForm.mobile,
      password: loginForm.password
    })
    clearLoginForm()
    emit('login-success', res.data)
    emit('message', '登入成功', 'success')
  } catch (error) {
    emit('message', getErrorMessage(error, '登入失敗'), 'danger')
  }
}
</script>

<template>
  <section class="row g-4">
    <div class="col-lg-6">
      <div class="card h-100">
        <div class="card-header bg-white">
          <h2 class="h5 mb-0">註冊</h2>
        </div>
        <form class="card-body" @submit.prevent="register">
          <div class="mb-3">
            <label class="form-label">手機號碼</label>
            <input v-model="registerForm.mobile" class="form-control" type="tel" placeholder="0912345678" />
          </div>
          <div class="mb-3">
            <label class="form-label">使用者名稱</label>
            <input v-model="registerForm.userName" class="form-control" type="text" />
          </div>
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input v-model="registerForm.email" class="form-control" type="email" />
          </div>
          <div class="mb-3">
            <label class="form-label">密碼</label>
            <input v-model="registerForm.password" class="form-control" type="password" />
          </div>
          <button class="btn btn-primary w-100" type="submit">註冊</button>
        </form>
      </div>
    </div>

    <div class="col-lg-6">
      <div class="card h-100">
        <div class="card-header bg-white">
          <h2 class="h5 mb-0">登入</h2>
        </div>

        <div v-if="user" class="card-body">
          <p class="mb-3">你已經登入為 {{ user.userName }}，若要登入其他帳號請先登出。</p>
          <button class="btn btn-outline-secondary" @click="emit('logout')">登出</button>
        </div>

        <form v-else class="card-body" @submit.prevent="login">
          <div class="mb-3">
            <label class="form-label">手機號碼</label>
            <input v-model="loginForm.mobile" class="form-control" type="tel" placeholder="0912345678" />
          </div>
          <div class="mb-3">
            <label class="form-label">密碼</label>
            <input v-model="loginForm.password" class="form-control" type="password" />
          </div>
          <button class="btn btn-success w-100" type="submit">登入</button>
        </form>
      </div>
    </div>
  </section>
</template>
