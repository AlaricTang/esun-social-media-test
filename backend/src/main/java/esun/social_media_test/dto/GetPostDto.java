package esun.social_media_test.dto;

import java.time.LocalDateTime;

public interface GetPostDto {

	Integer getPostId();

	Integer getUserId();

	String getUserName();

	String getContent();

	String getImage();

	LocalDateTime getCreatedAt();
}
