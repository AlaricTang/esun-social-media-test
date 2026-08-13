package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginReq {

	@NotBlank(message = "mobile is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "mobile format is invalid")
	private String mobile;

	@NotBlank(message = "password is required")
	private String password;
}
