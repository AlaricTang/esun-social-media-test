package esun.social_media_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.dto.CreatePostReq;
import esun.social_media_test.dto.GetPostDto;
import esun.social_media_test.dto.UpdatePostReq;
import esun.social_media_test.entity.Post;
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

	/** ?潭? */
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

	/** ?????蝡?*/
	@Override
	@Transactional
	public void updatePost(Integer postId, UpdatePostReq req) throws DataNotFoundException {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new DataNotFoundException("post not found"));

		if (!post.getUserId().equals(req.getUserId())) {
			throw new DataNotFoundException("post not found");
		}

		post.setContent(req.getContent());
		post.setImage(req.getImage());
		postRepo.save(post);
	}

	@Override
	@Transactional
	public void deletePost(Integer postId, Integer userId) throws DataNotFoundException {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new DataNotFoundException("post not found"));

		if (!post.getUserId().equals(userId)) {
			throw new DataNotFoundException("post not found");
		}

		postRepo.delete(post);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GetPostDto> getAllPosts() {
		return postRepo.getAllPostsWithUser();
	}
}
