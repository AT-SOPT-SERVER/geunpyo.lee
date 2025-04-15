package org.sopt.service;

import static java.lang.reflect.Array.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.Title;
import org.sopt.dto.PostResponse;
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
	public void createPost(String title) {
		Title validTitle = checkDuplicate(title);
		checkLastPostTime();
		Post post = new Post(validTitle);
		postRepository.save(post);
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
		Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
		return PostResponse.from(post);
	}

	@Transactional
	public void deletePostById(int id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
		postRepository.delete(post);
	}

	@Transactional
	public void updatePost(int postId, String title) {
		checkDuplicate(title);
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
		post.updatePost(title);
	}

	@Transactional(readOnly = true)
	public List<Post> searchPost(String keyword) {
		if (getLength(keyword.length()) < 2) {
			throw new IllegalArgumentException("검색은 두 글자 이상부터 가능합니다.");
		}
		return postRepository.findByTitle_ContentContaining(keyword);
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

	private Title checkDuplicate(String content) {
		Title title = new Title(content);
		if (postRepository.existsByTitle(title)) {
			throw new RuntimeException("이미 동일한 내용의 게시물이 있습니다.");
		}
		return title;
	}
}
