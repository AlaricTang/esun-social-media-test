package esun.social_media_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esun.social_media_test.entity.Comment;
import esun.social_media_test.repository.CommentRepository;
import esun.social_media_test.service.impl.CommentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;

	@Override
	@Transactional
	public Comment addComment(Integer userId, Integer postId, String content) {
		if (content == null || content.trim().isEmpty()) {
			log.warn("新增留言失敗：留言內容為空 - userId: {}, postId: {}", userId, postId);
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
