package esun.social_media_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import esun.social_media_test.entity.User;
import esun.social_media_test.repository.UserRepository;
import esun.social_media_test.service.impl.UserServiceImpl;
import esun.social_media_test.utils.PasswordUtil;

public class UserService implements UserServiceImpl {

	@Autowired
	private UserRepository userRepo;

	@Override
	public void register(String mobile, String userName, String email, String rawPassword) {
		// 1. 檢查手機號碼是否已被註冊
        Optional<User> existingUser = userRepo.getUserByMobile(mobile);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("該手機號碼已被註冊！");
        }

        // 2. 密碼加鹽雜湊處理
        String salt = PasswordUtil.generateSalt();
        String passwordHash = PasswordUtil.hashPassword(rawPassword, salt);

        // 3. 呼叫 Repository 執行 register_user 預存程序
        userRepo.registerUser(mobile, userName, email, passwordHash, salt);
	}

	@Override
	public User login(String mobile, String rawPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
