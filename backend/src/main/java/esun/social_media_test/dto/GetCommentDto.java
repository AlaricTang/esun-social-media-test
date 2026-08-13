package esun.social_media_test.dto;

import java.time.LocalDateTime;

public interface GetCommentDto {

	Integer getCommentId();

	Integer getUserId();

	String getUserName();

	Integer getPostId();

	String getContent();

	LocalDateTime getCreatedAt();
}
