package esun.social_media_test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.repository.PostRepository;
import esun.social_media_test.service.impl.PostServiceImpl;

@Service
public class PostService implements PostServiceImpl {

	@Autowired
	private PostRepository postRepo;

	@Override
	@Transactional
	public void createPost(Integer userId, String content, String image) {
		if (content == null || content.trim().isEmpty()) {
			throw new IllegalArgumentException("發文內容不能為空！");
		}

		postRepo.createPost(userId, content, image);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAllPosts() {
		return postRepo.getAllPostsWithUser();
	}
}
