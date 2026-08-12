package esun.social_media_test.service.impl;

import java.util.List;

import esun.social_media_test.dto.AddCommentReq;
import esun.social_media_test.dto.AddCommentResp;
import esun.social_media_test.entity.Comment;
import esun.social_media_test.exception.DataNotFoundException;

public interface CommentService {

	/** 新增留言 */
	AddCommentResp addComment(AddCommentReq req) throws DataNotFoundException;

	/** 依文章 ID 查詢所有留言 */
	List<Comment> getCommentsByPostId(Integer postId) throws DataNotFoundException;
}
