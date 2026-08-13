export function formatDate(value) {
  if (!value) return ''
  return new Date(value).toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

export function getErrorMessage(error, fallback) {
  return error?.response?.data?.message || fallback
}

export function normalizePosts(data) {
  const list = data?.AllPostsList || data?.allPostsList || []
  return Array.isArray(list) ? list : []
}
