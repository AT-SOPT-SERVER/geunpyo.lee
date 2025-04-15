package org.sopt.controller;

import java.util.List;

import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/post")
	public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
		postService.createPost(postRequest.getTitle());
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/posts/all")
	public ResponseEntity<?> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPost());
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<PostResponse> getPostById(@PathVariable int id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deletePostById(@PathVariable int id) {
		postService.deletePostById(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PutMapping("/post/{id}")
	public void updatePost(@PathVariable int id, @RequestBody PostRequest request) {
		postService.updatePost(id, request.getTitle());
	}

	@GetMapping("/posts")
	public List<Post> search(@RequestParam String keyword) {
		return postService.searchPost(keyword);
	}
}
