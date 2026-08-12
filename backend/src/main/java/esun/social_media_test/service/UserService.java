package esun.social_media_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import esun.social_media_test.dto.GetUserByMobileReq;
import esun.social_media_test.dto.GetUserByMobileResp;
import esun.social_media_test.entity.User;
import esun.social_media_test.repository.UserRepository;
import esun.social_media_test.service.impl.UserServiceImpl;
import esun.social_media_test.utils.PasswordUtil;

public class UserService implements UserServiceImpl {

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private UserRepository userRepo;

	@Override
	public void register(String mobile, String userName, String email, String rawPassword) {

		// 檢查手機號碼是否已被註冊
		Optional<User> existingUser = userRepo.getUserByMobile(mobile);
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("該手機號碼已被註冊！");
		}

		// 密碼加鹽雜湊處理
		String salt = PasswordUtil.generateSalt();
		String passwordHash = PasswordUtil.hashPassword(rawPassword, salt);

		// 執行 註冊User
		userRepo.registerUser(mobile, userName, email, passwordHash, salt);
	}

	@Override
	public User login(String mobile, String rawPassword) {
		// 依手機號碼查詢使用者 (呼叫 Stored Procedure: get_user_by_mobile)
		User user = userRepo.getUserByMobile(mobile).orElseThrow(() -> new IllegalArgumentException("帳號或密碼錯誤！"));

		// 取出 DB 裡的 salt，對輸入的明文密碼重新計算雜湊
		String inputHash = PasswordUtil.hashPassword(rawPassword, user.getSalt());

		// 比對雜湊值
		if (!inputHash.equals(user.getPassword())) {
			throw new IllegalArgumentException("帳號或密碼錯誤！");
		}
		return user;
	}

	@Override
	public GetUserByMobileResp getUserByMobile(GetUserByMobileReq req) {
		User user = userRepo.getUserByMobile(req.getMobile()).orElseThrow(() -> new IllegalArgumentException("查無此使用者"));
		return objMapper.convertValue(user, GetUserByMobileResp.class);
	}

}
