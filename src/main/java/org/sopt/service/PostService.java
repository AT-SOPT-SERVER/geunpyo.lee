package org.sopt.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.sopt.domain.Content;
import org.sopt.domain.Post;
import org.sopt.domain.Title;
import org.sopt.domain.User;
import org.sopt.domain.constant.Tag;
import org.sopt.dto.PostCreateRequest;
import org.sopt.dto.PostDetailResponse;
import org.sopt.dto.PostResponse;
import org.sopt.dto.PostUpdateRequest;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.PostTitleDuplicateException;
import org.sopt.exception.RequestCooldownException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private static final Duration POST_CREATION_COOLDOWN = Duration.ofMinutes(3);

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostCacheService postCacheService;

	public PostService(PostRepository postRepository, UserRepository userRepository,
		PostCacheService postCacheService) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postCacheService = postCacheService;
	}

	@Transactional
	public PostResponse createPost(int userId, PostCreateRequest postCreateRequest) {
		User user = findUserById(userId);

		validatePostTitle(postCreateRequest.title());

		checkUserCooldown(userId);

		Post post = buildPostFrom(postCreateRequest, user);
		Post savedPost = postRepository.save(post);

		postCacheService.updateUserLastPostTime(userId, LocalDateTime.now());

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
	public PostDetailResponse getPostById(int postId) {
		Post post = findPostById(postId);
		return PostDetailResponse.from(post);
	}

	@Transactional
	public void deletePostById(int postId) {
		Post post = findPostById(postId);
		postRepository.delete(post);
	}

	@Transactional
	public void updatePost(int postId, PostUpdateRequest request) {
		String title = request.title();

		validatePostTitle(title);

		Post post = findPostById(postId);
		post.updatePost(title, request.content());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> searchPost(String keyword, Tag tag) {
		List<Post> posts = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		return posts.stream()
			.map(PostResponse::from)
			.toList();
	}

	private User findUserById(int userId) {
		return userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
	}

	private Post findPostById(int postId) {
		return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
	}

	private void validatePostTitle(String title) {
		if (postRepository.existsByTitle_Content(title)) {
			throw new PostTitleDuplicateException();
		}
	}

	private Post buildPostFrom(PostCreateRequest request, User user) {
		Title validTitle = new Title(request.title());
		Content content = new Content(request.content());
		Tag tag = request.tag();

		return Post.create(validTitle, content, tag, user);
	}

	private void checkUserCooldown(int userId) {
		Optional<LocalDateTime> lastPostTimeOpt = postCacheService.getUserLastPostTime(userId, POST_CREATION_COOLDOWN);

		if (lastPostTimeOpt.isEmpty()) {
			return;
		}

		LocalDateTime lastPostTime = lastPostTimeOpt.get();
		LocalDateTime now = LocalDateTime.now();
		Duration sinceLastPost = Duration.between(lastPostTime, now);

		if (sinceLastPost.compareTo(POST_CREATION_COOLDOWN) < 0) {
			throw new RequestCooldownException();
		}
	}
}
