package esun.social_media_test.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetUserByMobileResp {
	private Integer userId;
	private String userName;
	private String email;
    private String mobile;
	private String biography;
	private String coverImage;
    private LocalDateTime createdAt;
}
