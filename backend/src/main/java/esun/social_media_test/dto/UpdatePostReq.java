package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePostReq {

	@NotNull(message = "userId is required")
	private Integer userId;

	@NotBlank(message = "content is required")
	private String content;

	private String image;
}
