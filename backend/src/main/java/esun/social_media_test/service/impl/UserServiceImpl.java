package esun.social_media_test.service.impl;

import java.util.Optional;

import esun.social_media_test.entity.User;

public interface UserServiceImpl {
	
	/** 註冊使用者 */
	void register(String mobile, String userName, String email, String rawPassword);

	/** 登入並驗證 */
	User login(String mobile, String rawPassword);

	/** 取得使用者資料 by 手機 */
	Optional<User> getUserByMobile(String mobile);
}
