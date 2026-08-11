-- 使用者
CREATE TABLE IF NOT EXISTS sys_user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    mobile VARCHAR(20) NOT NULL UNIQUE, -- 手機號碼(登入與註冊用)
    salt VARCHAR(64), -- 登入驗證密碼用鹽
    biography TEXT, -- 自我介紹
    cover_image VARCHAR(255), -- 封面照片
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 發文
CREATE TABLE IF NOT EXISTS post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,	-- user_id FK to [sys_user].user_id 
    content TEXT NOT NULL,	-- 文章內容
    image VARCHAR(255),		-- 文章圖片路徑
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(user_id) ON DELETE CASCADE 
);

-- 留言
CREATE TABLE IF NOT EXISTS comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,	-- user_id FK to [sys_user].user_id 
    post_id INT NOT NULL,	-- post_id FK to [post].post_id
    content TEXT NOT NULL,	-- 留言內容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE
);

-- 建立 Stored Procedures
-- DELIMITER 重新定義分隔符號
DELIMITER // 

-- 註冊 user
CREATE PROCEDURE register_user(
    IN p_mobile VARCHAR(20),
    IN p_user_name VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_password_hash VARCHAR(255),
    IN p_salt VARCHAR(64)
)
BEGIN
    INSERT INTO sys_user (mobile, user_name, email, password, salt)
    VALUES (p_mobile, p_user_name, p_email, p_password_hash, p_salt);
END //

-- 查詢使用者 by mobile (用於登入驗證)
CREATE PROCEDURE get_user_by_mobile(
    IN p_mobile VARCHAR(20)
)
BEGIN
    SELECT * FROM sys_user WHERE mobile = p_mobile;
END //

-- 發文
CREATE PROCEDURE create_post(
    IN p_user_id INT,
    IN p_content TEXT,
    IN p_image VARCHAR(255)
)
BEGIN
    INSERT INTO post (user_id, content, image) 
    VALUES (p_user_id, p_content, p_image);
END //

-- 查詢所有文章(倒序)
CREATE PROCEDURE get_all_posts()
BEGIN
    SELECT p.post_id, p.user_id, u.user_name, p.content, p.image, p.created_at
    FROM post p
    JOIN sys_user u ON p.user_id = u.user_id
    ORDER BY p.created_at DESC;
END //

DELIMITER ; 