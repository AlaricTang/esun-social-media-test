package esun.social_media_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esun.social_media_test.dto.AddCommentReq;
import esun.social_media_test.dto.AddCommentResp;
import esun.social_media_test.dto.GetCommentsByPostIdResp;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.exception.ErrorInputException;
import esun.social_media_test.service.impl.CommentService;
import jakarta.validation.Valid;

/**
 * 文章 Controller
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController extends ControllerBase {

	@Autowired
	private CommentService commentService;

	/** 新增留言 */
	@PostMapping("/comments")
	public ResponseEntity<AddCommentResp> addComment(@Valid @RequestBody AddCommentReq req, Errors err)
			throws ErrorInputException, DataNotFoundException {
		validateRequest(err);
		AddCommentResp comment = commentService.addComment(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	/** 查詢所有留言 by postId */
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<GetCommentsByPostIdResp> getCommentsByPostId(@PathVariable Integer postId)
			throws DataNotFoundException {
		return ResponseEntity.ok(new GetCommentsByPostIdResp(commentService.getCommentsByPostId(postId)));
	}

}
