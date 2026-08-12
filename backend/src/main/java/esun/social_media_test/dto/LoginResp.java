package esun.social_media_test.dto;

import lombok.Data;

@Data
public class LoginResp {

	private Integer userId;
	private String userName;
	private String email;
	private String mobile;
	private String biography;
	private String coverImage;

}
