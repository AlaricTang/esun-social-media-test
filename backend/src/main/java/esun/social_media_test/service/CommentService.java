package esun.social_media_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.entity.Comment;
import esun.social_media_test.repository.CommentRepository;
import esun.social_media_test.service.impl.CommentServiceImpl;

public class CommentService implements CommentServiceImpl {

	@Autowired
	private CommentRepository commentRepo;

	@Override
	@Transactional
	public Comment addComment(Integer userId, Integer postId, String content) {
		if (content == null || content.trim().isEmpty()) {
			throw new IllegalArgumentException("留言內容不能為空！");
		}

		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setPostId(postId);
		comment.setContent(content);

		return commentRepo.save(comment);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> getCommentsByPostId(Integer postId) {
		return commentRepo.findByPostIdOrderByCreatedAtAsc(postId);
	}

}
