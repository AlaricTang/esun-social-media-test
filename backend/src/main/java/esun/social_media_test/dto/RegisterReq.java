package esun.social_media_test.dto;

import lombok.Data;

@Data
public class RegisterReq {
	
	private String mobile;
	private String userName;
	private String email;
	private String password;
}
