package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginReq {
	
	@NotBlank(message="mobile 不得為空")
	@Pattern(regexp = "^[0-9]{10}$", message="mobile 只能為10碼數字")
	private String mobile;
	
	@NotBlank(message="password 不得為空")
	private String password;
}
