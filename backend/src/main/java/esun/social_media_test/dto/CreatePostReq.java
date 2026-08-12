package esun.social_media_test.dto;

import lombok.Data;

@Data
public class CreatePostReq {
	private Integer userId;
	private String content;
	private String image;
}
