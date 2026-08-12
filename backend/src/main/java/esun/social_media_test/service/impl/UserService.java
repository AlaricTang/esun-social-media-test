package esun.social_media_test.service.impl;

import esun.social_media_test.dto.GetUserByMobileReq;
import esun.social_media_test.dto.GetUserByMobileResp;
import esun.social_media_test.dto.LoginReq;
import esun.social_media_test.dto.LoginResp;
import esun.social_media_test.dto.RegisterReq;
import esun.social_media_test.exception.DataDuplicateException;
import esun.social_media_test.exception.DataNotFoundException;

public interface UserService {

	/** 註冊使用者 */
	void register(RegisterReq req) throws DataDuplicateException;

	/** 登入並驗證 */
	LoginResp login(LoginReq req) throws DataNotFoundException;

	/** 取得使用者資料 by 手機 */
	GetUserByMobileResp getUserByMobile(GetUserByMobileReq req) throws DataNotFoundException;
}
