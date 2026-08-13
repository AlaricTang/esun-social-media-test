package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GetUserByMobileReq {

	@NotBlank(message = "mobile is required")
	@Pattern(regexp = "^09\\d{8}$", message = "mobile format is invalid")
	private String mobile;
}
