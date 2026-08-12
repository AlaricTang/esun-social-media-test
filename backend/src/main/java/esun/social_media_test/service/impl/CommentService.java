package esun.social_media_test.service.impl;

import java.util.List;

import esun.social_media_test.entity.Comment;

public interface CommentService {

	/** 新增留言 */
	Comment addComment(Integer userId, Integer postId, String content);

	/** 依文章 ID 查詢所有留言 */
	List<Comment> getCommentsByPostId(Integer postId);
}
