package esun.social_media_test.dto;

import java.util.List;

import esun.social_media_test.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCommentsByPostIdResp {
	private List<Comment> commentsList;
}
