package esun.social_media_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.dto.AddCommentReq;
import esun.social_media_test.dto.AddCommentResp;
import esun.social_media_test.entity.Comment;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.repository.CommentRepository;
import esun.social_media_test.repository.PostRepository;
import esun.social_media_test.repository.UserRepository;
import esun.social_media_test.service.impl.CommentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	@Transactional
	public AddCommentResp addComment(AddCommentReq req) throws DataNotFoundException {

		Integer userId = req.getUserId();
		Integer postId = req.getPostId();
		String content = req.getContent();

		if (!userRepo.existsById(userId)) {
			throw new DataNotFoundException("user not found");
		}
		if (!postRepo.existsById(postId)) {
			throw new DataNotFoundException("post not found");
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setPostId(postId);
		comment.setContent(content);

		return new AddCommentResp(commentRepo.save(comment));
	}

	/** 依文章 ID 查詢所有留言 */
	@Override
	@Transactional(readOnly = true)
	public List<Comment> getCommentsByPostId(Integer postId) throws DataNotFoundException {
		if (!postRepo.existsById(postId)) {
			throw new DataNotFoundException("post not found");
		}
		return commentRepo.findByPostIdOrderByCreatedAtAsc(postId);
	}

}
