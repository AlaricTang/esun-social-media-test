package esun.social_media_test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.repository.PostRepository;
import esun.social_media_test.service.impl.PostService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Override
	@Transactional
	public void createPost(Integer userId, String content, String image) {
		if (content == null || content.trim().isEmpty()) {
			log.warn("新增貼文失敗：內容為空 - userId: {}", userId);
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
