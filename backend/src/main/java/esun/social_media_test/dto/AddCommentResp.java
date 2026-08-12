package esun.social_media_test.dto;

import esun.social_media_test.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCommentResp {
	private Comment comment;
}
