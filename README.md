# esun-social-media-test

簡易社群媒體平台，使用 Vue.js、Spring Boot、MySQL 實作。


## 專案結構

```text
social-media-test
├── backend
├── frontend
├── DB
│   ├── schema.sql
│   └── data.sql
└── docker-compose.yml
```

## 啟動方式

### 1. 啟動資料庫

使用 Docker：

```bash
docker-compose up -d
```

若沒有 Docker，可使用本機 MySQL，先建立資料庫 `esun_social_media`，再依序執行：

```bash
DB/schema.sql
DB/data.sql
```

### 2. 啟動後端

```bash
cd backend
mvn spring-boot:run
```

後端網址：

```text
https://localhost:8080
```

### 3. 啟動前端

```bash
cd frontend
npm install
npm run dev
```

前端網址：

```text
http://localhost:5173
```

## 其他指令

停止 Docker 資料庫：

```bash
docker-compose stop
```

移除 Docker 資料庫資料：

```bash
docker-compose down -v
```
