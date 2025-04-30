package org.sopt.controller;

import java.util.List;

import org.sopt.dto.PostCreateRequest;
import org.sopt.dto.PostDetailResponse;
import org.sopt.dto.PostResponse;
import org.sopt.dto.PostUpdateRequest;
import org.sopt.global.common.dto.ResponseDto;
import org.sopt.service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/posts")
	public ResponseDto<PostResponse> createPost(@RequestHeader Integer userId,
		@RequestBody PostCreateRequest postCreateRequest) {
		PostResponse response = postService.createPost(userId, postCreateRequest);
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
	public ResponseDto<Void> deletePostById(@PathVariable int id) {
		postService.deletePostById(id);
		return ResponseDto.okWithoutContent();
	}

	@PutMapping("/posts/{id}")
	public ResponseDto<Void> updatePost(@PathVariable int id, @RequestBody PostUpdateRequest request) {
		postService.updatePost(id, request);
		return ResponseDto.okWithoutContent();
	}

	@GetMapping("/posts/search")
	public ResponseDto<List<PostResponse>> search(@RequestParam String keyword) {
		List<PostResponse> postResponses = postService.searchPost(keyword);
		return ResponseDto.ok(postResponses);
	}
}
