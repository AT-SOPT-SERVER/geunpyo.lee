package org.sopt.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.Title;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private static final Duration POST_CREATION_COOLDOWN = Duration.ofMinutes(3);
	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public PostResponse createPost(String title) {
		checkDuplicate(title);
		checkLastPostTime();

		Title validTitle = new Title(title);

		Post post = new Post(validTitle);
		Post savedPost = postRepository.save(post);

		return PostResponse.from(savedPost);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPost() {
		List<Post> posts = postRepository.findAll();

		return posts.stream()
			.map(PostResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public PostResponse getPostById(int id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		return PostResponse.from(post);
	}

	@Transactional
	public void deletePostById(int id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		postRepository.delete(post);
	}

	@Transactional
	public void updatePost(int postId, String title) {
		checkDuplicate(title);
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
		post.updatePost(title);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> searchPost(String keyword) {
		List<Post> posts = postRepository.findByTitle_ContentContaining(keyword);

		return posts.stream()
			.map(PostResponse::from)
			.toList();
	}

	private void checkLastPostTime() {

		Optional<Post> latestPost = postRepository.findTopByOrderByCreatedAtDesc();

		if (latestPost.isEmpty()) {
			return;
		}

		LocalDateTime now = LocalDateTime.now();
		Duration sinceLastPost = Duration.between(latestPost.get().getCreatedAt(), now);

		if (sinceLastPost.compareTo(POST_CREATION_COOLDOWN) < 0) {
			long remainingSeconds = POST_CREATION_COOLDOWN.minus(sinceLastPost).getSeconds();
			throw new RuntimeException("도배 방지를 위해 " + remainingSeconds + "초 후에 다시 시도해주세요.");
		}
	}

	private void checkDuplicate(String title) {
		if (postRepository.existsByTitle_Content(title)) {
			throw new RuntimeException("이미 동일한 내용의 게시물이 있습니다.");
		}
	}
}
