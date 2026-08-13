import axios from 'axios'

// 註冊新帳號 (payload: { mobile, userName, email, password })
export function registerUser(payload) {
  return axios.post('/api/user/users', payload)
}

// 使用者登入 (payload: { mobile, password })
export function loginUser(payload) {
  return axios.post('/api/user/login', payload)
}

// 取得所有貼文列表 (包含貼文內容與發文者資訊)
export function getAllPosts() {
  return axios.get('/api/post/getAllPosts')
}

// 發布新貼文 (payload: { userId, content, image })
export function createPost(payload) {
  return axios.post('/api/post/createPost', payload)
}

// 編輯貼文內容 (payload: { userId, content, image })
export function updatePost(postId, payload) {
  return axios.put(`/api/post/posts/${postId}`, payload)
}

// 刪除貼文 (需帶入 userId 供後端驗證發文者權限)
export function deletePost(postId, userId) {
  return axios.delete(`/api/post/posts/${postId}/users/${userId}`)
}

// 取得特定貼文下的所有留言 (含留言者資訊)
export function getCommentsByPostId(postId) {
  return axios.get(`/api/comment/posts/${postId}/comments`)
}

// 送出新留言 (payload: { userId, postId, content })
export function addComment(payload) {
  return axios.post('/api/comment/comments', payload)
}