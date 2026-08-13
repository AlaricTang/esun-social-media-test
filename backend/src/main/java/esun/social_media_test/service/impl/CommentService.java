package esun.social_media_test.service.impl;

import java.util.List;

import esun.social_media_test.dto.AddCommentReq;
import esun.social_media_test.dto.AddCommentResp;
import esun.social_media_test.dto.GetCommentDto;
import esun.social_media_test.exception.DataNotFoundException;

public interface CommentService {

	AddCommentResp addComment(AddCommentReq req) throws DataNotFoundException;

	List<GetCommentDto> getCommentsByPostId(Integer postId) throws DataNotFoundException;
}
