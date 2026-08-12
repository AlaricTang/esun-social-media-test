package esun.social_media_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esun.social_media_test.dto.GetUserByMobileReq;
import esun.social_media_test.dto.GetUserByMobileResp;
import esun.social_media_test.dto.LoginReq;
import esun.social_media_test.dto.LoginResp;
import esun.social_media_test.dto.RegisterReq;
import esun.social_media_test.exception.DataDuplicateException;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.service.impl.UserService;
import jakarta.validation.Valid;

/**
 * 使用者 Controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends ControllerBase {

	@Autowired
	private UserService userService;

	/** 註冊使用者 */
	@PostMapping("/users")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterReq req, Errors err)
			throws DataDuplicateException {
		validateRequest(err);
		userService.register(req);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/** 登入並驗證 */
	@PostMapping("/login")
	public ResponseEntity<LoginResp> login(@Valid @RequestBody LoginReq req, Errors err) throws DataNotFoundException {
		validateRequest(err);

		return ResponseEntity.ok(userService.login(req));
	}

	/** 取得使用者資料 by 手機 */
	@PostMapping("/search")
	public ResponseEntity<GetUserByMobileResp> getUserByMobile(@Valid @RequestBody GetUserByMobileReq req, Errors err)
			throws DataNotFoundException {
		validateRequest(err);
		return ResponseEntity.ok(userService.getUserByMobile(req));
	}

}
