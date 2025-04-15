package org.sopt.controller;

import java.util.List;

import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/post")

	public void createPost(@RequestBody PostRequest postRequest) {
		postService.createPost(postRequest.getTitle());
	}

	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPost());
	}

	@GetMapping("/post/{id}")
	public Post getPostById(@PathVariable int id) {
		return postService.getPostById(id);
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deletePostById(@PathVariable int id) {
		postService.deletePostById(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	public void updatePost(int id, String title) {
		postService.updatePost(id, title);
	}

	public List<Post> search(String keyword) {
		return postService.searchPost(keyword);
	}
}
