package esun.social_media_test.dto;

import lombok.Data;

@Data
public class AddCommentReq {
	private Integer userId;
    private Integer postId;
    private String content;
}
