package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterReq {

	@NotBlank(message = "mobile is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "mobile format is invalid")
	private String mobile;

	@NotBlank(message = "userName is required")
	private String userName;

	@Pattern(regexp = "(^$)|(^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$)", message = "email format is invalid")
	private String email;

	@NotBlank(message = "password is required")
	private String password;
}
