package esun.social_media_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCommentReq {
	
	@NotNull(message = "user Id 不得為空")
	private Integer userId;
	
	@NotNull(message = "post Id 不得為空")
    private Integer postId;
	
	@NotBlank(message="content 不得為空")
    private String content;
}
