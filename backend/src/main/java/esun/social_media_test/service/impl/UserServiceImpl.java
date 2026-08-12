package esun.social_media_test.service.impl;

import esun.social_media_test.dto.GetUserByMobileReq;
import esun.social_media_test.dto.GetUserByMobileResp;
import esun.social_media_test.entity.User;

public interface UserServiceImpl {

	/** 註冊使用者 */
	void register(String mobile, String userName, String email, String rawPassword);

	/** 登入並驗證 */
	User login(String mobile, String rawPassword);

	/** 取得使用者資料 by 手機 */
	GetUserByMobileResp getUserByMobile(GetUserByMobileReq req);
}
