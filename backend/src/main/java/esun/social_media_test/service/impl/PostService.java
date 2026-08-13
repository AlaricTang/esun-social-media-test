package esun.social_media_test.service.impl;

import java.util.List;

import esun.social_media_test.dto.CreatePostReq;
import esun.social_media_test.dto.GetPostDto;
import esun.social_media_test.dto.UpdatePostReq;
import esun.social_media_test.exception.DataNotFoundException;

public interface PostService {

	void createPost(CreatePostReq req) throws DataNotFoundException;

	void updatePost(Integer postId, UpdatePostReq req) throws DataNotFoundException;

	void deletePost(Integer postId, Integer userId) throws DataNotFoundException;

	List<GetPostDto> getAllPosts();
}
