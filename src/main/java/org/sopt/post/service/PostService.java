package org.sopt.post.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.sopt.post.controller.request.PostUpdateRequest;
import org.sopt.post.controller.response.PostDetailResponse;
import org.sopt.post.controller.response.PostResponse;
import org.sopt.post.domain.Content;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Title;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.exception.AccessDeniedException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.PostTitleDuplicateException;
import org.sopt.post.exception.RequestCooldownException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.request.PostCreateCommand;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
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
	public PostResponse createPost(int userId, PostCreateCommand command) {
		User user = findUserById(userId);
		checkUserCooldown(userId);

		validatePostTitle(command.title());

		Post post = buildPostFrom(command.title(), command.content(), command.tags(), user);
		Post savedPost = postRepository.save(post);

		postCacheService.updateUserLastPostTime(userId, LocalDateTime.now());

		return PostResponse.from(savedPost);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPost() {
		List<Post> posts = postRepository.findAllOrderByOrderByCreatedAtDesc();

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
	public void deletePostById(int userId, int postId) {
		User user = findUserById(userId);
		Post post = findPostById(postId);

		checkAuthentication(user, post);

		postRepository.delete(post);
	}

	@Transactional
	public void updatePost(int userId, int postId, PostUpdateRequest request) {
		Post post = findPostById(postId);
		User user = findUserById(userId);

		checkAuthentication(user, post);

		String title = request.title();

		validatePostTitle(title);
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

	private Post buildPostFrom(String title, String content, List<Tag> tags, User user) {
		Title validTitle = new Title(title);
		Content validContent = new Content(content);

		return Post.create(validTitle, validContent, tags, user);
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

	private void checkAuthentication(User user, Post post) {
		if (post.getUser().equals(user)) {
			throw new AccessDeniedException();
		}
	}
}
