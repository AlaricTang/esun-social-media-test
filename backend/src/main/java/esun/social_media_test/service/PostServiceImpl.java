package esun.social_media_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.dto.CreatePostReq;
import esun.social_media_test.dto.GetPostDto;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.repository.PostRepository;
import esun.social_media_test.repository.UserRepository;
import esun.social_media_test.service.impl.PostService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	/** 發文 */
	@Override
	@Transactional
	public void createPost(CreatePostReq req) throws DataNotFoundException {
		String content = req.getContent();
		Integer userId = req.getUserId();

		if (!userRepo.existsById(userId)) {
			throw new DataNotFoundException("user not found");
		}
		postRepo.createPost(userId, content, req.getImage());
	}

	/** 取得所有文章 */
	@Override
	@Transactional(readOnly = true)
	public List<GetPostDto> getAllPosts() {
		return postRepo.getAllPostsWithUser();
	}
}
