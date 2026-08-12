package esun.social_media_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esun.social_media_test.dto.CreatePostReq;
import esun.social_media_test.dto.GetAllPostsResp;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.service.impl.PostService;
import jakarta.validation.Valid;

/**
 * 文章 Controller
 */
@RestController
@RequestMapping("/api/post")
public class PostController extends ControllerBase {

	@Autowired
	private PostService postService;

	/** 發布新文章 */
	@PostMapping("/createPost")
	public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostReq req, Errors err)
			throws DataNotFoundException {
		validateRequest(err);
		postService.createPost(req);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/** 取得所有文章 */
	@GetMapping("/getAllPosts")
	public ResponseEntity<GetAllPostsResp> getAllPosts() {
		return ResponseEntity.ok(new GetAllPostsResp(postService.getAllPosts()));
	}
}
