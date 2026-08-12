package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GetUserByMobileReq {
	
	@NotBlank(message = "手機號碼不能為空")
	@Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式不正確")
	private String mobile;
}
