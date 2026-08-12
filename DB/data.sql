-- 匯入測試data
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
-- user
INSERT INTO sys_user (user_id, mobile, user_name, email, password, salt, biography, cover_image) 
VALUES 
(1, '0912345678', '王小明', 'xiaoming@example.com', '$2a$10$e8R6.yZ2jD7vH5pZ2jD7vO1z1z1z1z1z1z1z1z1z1z1z1z1z1z1z1', 'a1b2c3d4e5f6', '大家好！我是小明，喜歡前後端開發與分享生活。', 'https://picsum.photos/800/300?random=1'),
(2, '0987654321', '陳美麗', 'meili@example.com', '$2a$10$e8R6.yZ2jD7vH5pZ2jD7vO1z1z1z1z1z1z1z1z1z1z1z1z1z1z1z1', 'f6e5d4c3b2a1', '熱愛美食與旅遊的全端工程師 🚀', 'https://picsum.photos/800/300?random=2'),
(3, '0911222333', '張阿強', 'john@example.com', '$2a$10$e8R6.yZ2jD7vH5pZ2jD7vO1z1z1z1z1z1z1z1z1z1z1z1z1z1z1z1', '9876543210ab', '玉山銀行的忠實用戶，挑戰實作社交平台中！', 'https://picsum.photos/800/300?random=3');

-- post
INSERT INTO post (post_id, user_id, content, image, created_at)
VALUES 
(1, 1, '今天順利完成 Docker Compose 與 MySQL 資料庫自動建表設定，太有成就感了！🎉', 'https://picsum.photos/600/400?random=10', NOW() - INTERVAL 2 DAY),
(2, 2, '推薦大家這家在台北超好吃的拉麵店！湯頭濃郁麵條Q彈 🍜', 'https://picsum.photos/600/400?random=11', NOW() - INTERVAL 1 DAY),
(3, 3, '有人也在準備 Spring Boot + Vue 3 的專案實作嗎？一起加油打氣！💪', NULL, NOW() - INTERVAL 2 HOUR);

-- comment
INSERT INTO comment (comment_id, user_id, post_id, content, created_at)
VALUES 
(1, 2, 1, '恭喜小明！Docker Compose 真的超級方便～', NOW() - INTERVAL 1 DAY),
(2, 3, 1, '太強了，預存程序 (Stored Procedure) 也有寫出來嗎？', NOW() - INTERVAL 20 HOUR),
(3, 1, 2, '求店名！下次也想去吃看看！😋', NOW() - INTERVAL 12 HOUR),
(4, 1, 3, '加油強哥！遇到問題可以互相討論聊天喔！', NOW() - INTERVAL 1 HOUR);