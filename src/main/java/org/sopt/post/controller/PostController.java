package org.sopt.post.controller;

import java.util.List;

import org.sopt.global.common.annotation.Auth;
import org.sopt.global.common.dto.ResponseDto;
import org.sopt.post.controller.request.PostCreateRequest;
import org.sopt.post.controller.request.PostUpdateRequest;
import org.sopt.post.controller.response.PostDetailResponse;
import org.sopt.post.controller.response.PostLikeChangeResponse;
import org.sopt.post.controller.response.PostResponse;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.service.PostLikeService;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
	private final PostService postService;
	private final PostLikeService postLikeService;

	public PostController(PostService postService, PostLikeService postLikeService) {
		this.postService = postService;
		this.postLikeService = postLikeService;
	}

	@PostMapping("/posts")
	public ResponseDto<PostResponse> createPost(@Auth User user,
		@RequestBody PostCreateRequest postCreateRequest) {
		PostResponse response = postService.createPost(user, postCreateRequest.toCommand());
		return ResponseDto.created(response);
	}

	@GetMapping("/posts")
	public ResponseDto<List<PostResponse>> getAllPosts() {
		return ResponseDto.ok(postService.getAllPost());
	}

	@GetMapping("/posts/{id}")
	public ResponseDto<PostDetailResponse> getPostById(@PathVariable int id) {
		return ResponseDto.ok(postService.getPostById(id));
	}

	@DeleteMapping("/posts/{id}")
	public ResponseDto<Void> deletePostById(@Auth User user, @PathVariable int id) {
		postService.deletePostById(user, id);
		return ResponseDto.okWithoutContent();
	}

	@PutMapping("/posts/{id}")
	public ResponseDto<Void> updatePost(@Auth User user, @PathVariable int id,
		@RequestBody PostUpdateRequest request) {
		postService.updatePost(user, id, request);
		return ResponseDto.okWithoutContent();
	}

	@GetMapping("/posts/search")
	public ResponseDto<List<PostResponse>> search(@RequestParam(required = false) String keyword,
		@RequestParam(required = false) Tag tag) {
		List<PostResponse> postResponses = postService.searchPost(keyword, tag);
		return ResponseDto.ok(postResponses);
	}

	@PatchMapping("/posts/likes/{postId}")
	public ResponseDto<PostLikeChangeResponse> toggleLike(@Auth User user,
		@PathVariable long postId
	) {
		PostLikeChangeResponse response = postLikeService.toggleLike(user, postId);
		return ResponseDto.ok(response);
	}
}
