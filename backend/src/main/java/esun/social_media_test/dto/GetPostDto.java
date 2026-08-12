package esun.social_media_test.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

public interface GetPostDto {

	@Value("#{target.post_id}")
	Integer getPostId();

	@Value("#{target.user_id}")
	Integer getUserId();

	@Value("#{target.user_name}")
	String getUserName();

	String getContent();

	String getImage();

	@Value("#{target.created_at}")
	LocalDateTime getCreatedAt();
}
