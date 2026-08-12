package esun.social_media_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import esun.social_media_test.dto.GetUserByMobileReq;
import esun.social_media_test.dto.GetUserByMobileResp;
import esun.social_media_test.dto.LoginReq;
import esun.social_media_test.dto.LoginResp;
import esun.social_media_test.dto.RegisterReq;
import esun.social_media_test.entity.User;
import esun.social_media_test.exception.DataDuplicateException;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.repository.UserRepository;
import esun.social_media_test.service.impl.UserService;
import esun.social_media_test.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private UserRepository userRepo;

	/** 註冊使用者 */
	@Override
	public void register(RegisterReq req) throws DataDuplicateException {

		String mobile = req.getMobile();
		String userName = req.getUserName();

		// 檢核手機號碼是否已被註冊
		Optional<User> existingUser = userRepo.getUserByMobile(mobile);
		if (existingUser.isPresent()) {
			log.warn("使用者註冊失敗：手機號碼:{} 已存在", mobile);
			throw new DataDuplicateException("該手機號碼已被註冊！");
		}

		// 密碼加鹽雜湊處理
		String salt = PasswordUtil.generateSalt();
		String passwordHash = PasswordUtil.hashPassword(req.getPassword(), salt);

		// 執行 註冊User
		userRepo.registerUser(mobile, userName, req.getEmail(), passwordHash, salt);
		log.info("成功完成使用者註冊 - mobile: {}, userName: {}", mobile, userName);
	}

	/** 登入並驗證 */
	@Override
	public LoginResp login(LoginReq req) throws DataNotFoundException {
		// 查詢使用者 by 手機號碼
		User user = userRepo.getUserByMobile(req.getMobile())
				.orElseThrow(() -> new DataNotFoundException("user not found"));

		// 取出 DB 裡的 salt，對輸入的明文密碼重新計算雜湊
		String inputHash = PasswordUtil.hashPassword(req.getPassword(), user.getSalt());

		// 比對雜湊值
		if (!inputHash.equals(user.getPassword())) {
			log.warn("使用者登入失敗：密碼比對不符 - userId: {}", user.getUserId());
			throw new DataNotFoundException("帳號或密碼錯誤！");
		}

		log.info("使用者{}登入成功", user.getUserId());

		return objMapper.convertValue(user, LoginResp.class);
	}

	/** 取得使用者資料 by 手機 */
	@Override
	public GetUserByMobileResp getUserByMobile(GetUserByMobileReq req) throws DataNotFoundException {
		User user = userRepo.getUserByMobile(req.getMobile())
				.orElseThrow(() -> new DataNotFoundException("user not found"));
		return objMapper.convertValue(user, GetUserByMobileResp.class);
	}

}
