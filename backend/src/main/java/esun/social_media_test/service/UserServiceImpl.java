package esun.social_media_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	@Transactional
	public void register(RegisterReq req) throws DataDuplicateException {
		String mobile = req.getMobile();
		String userName = req.getUserName();

		Optional<User> existingUser = userRepo.getUserByMobile(mobile);
		if (existingUser.isPresent()) {
			log.warn("mobile already exists: {}", mobile);
			throw new DataDuplicateException("mobile already exists");
		}

		String salt = PasswordUtil.generateSalt();
		String passwordHash = PasswordUtil.hashPassword(req.getPassword(), salt);

		userRepo.registerUser(mobile, userName, req.getEmail(), passwordHash, salt);
		log.info("register user success - mobile: {}, userName: {}", mobile, userName);
	}

	@Override
	public LoginResp login(LoginReq req) throws DataNotFoundException {
		User user = userRepo.getUserByMobile(req.getMobile())
				.orElseThrow(() -> new DataNotFoundException("user not found"));

		String inputHash = PasswordUtil.hashPassword(req.getPassword(), user.getSalt());

		if (!inputHash.equals(user.getPassword())) {
			log.warn("login failed - userId: {}", user.getUserId());
			throw new DataNotFoundException("account or password is invalid");
		}

		log.info("login success - userId: {}", user.getUserId());

		return objMapper.convertValue(user, LoginResp.class);
	}

	@Override
	public GetUserByMobileResp getUserByMobile(GetUserByMobileReq req) throws DataNotFoundException {
		User user = userRepo.getUserByMobile(req.getMobile())
				.orElseThrow(() -> new DataNotFoundException("user not found"));
		return objMapper.convertValue(user, GetUserByMobileResp.class);
	}
}
