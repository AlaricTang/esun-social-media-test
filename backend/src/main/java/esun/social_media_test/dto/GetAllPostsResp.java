package esun.social_media_test.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllPostsResp {
	private List<GetPostDto> AllPostsList;
}
