package esun.social_media_test.service.impl;

import java.util.List;

import esun.social_media_test.dto.CreatePostReq;
import esun.social_media_test.dto.GetPostDto;
import esun.social_media_test.exception.DataNotFoundException;

public interface PostService {

	/** 發布新文章 */
	void createPost(CreatePostReq req) throws DataNotFoundException;

	/** 取得所有文章 */
	List<GetPostDto> getAllPosts();
}
