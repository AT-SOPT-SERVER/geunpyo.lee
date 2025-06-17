package org.sopt.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.post.domain.constant.Tag.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.repository.CommentLikeRepository;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.post.controller.response.PostDetailsResponse;
import org.sopt.post.controller.response.PostResponse;
import org.sopt.post.domain.Content;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostLike;
import org.sopt.post.domain.Title;
import org.sopt.post.exception.InvalidTagCountException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostLikeRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.request.PostCreateCommand;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PostServiceTest {

	@Autowired
	PostService postService;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CommentLikeRepository commentLikeRepository;
	@Autowired
	private PostLikeRepository postLikeRepository;

	@DisplayName("사용자는 게시글을 작성할 수 있다.")
	@Test
	void createPost() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);

		PostCreateCommand command = new PostCreateCommand(
			"testtitle",
			"testcontent",
			List.of(BE, ETC)
		);
		//when

		PostResponse response = postService.createPost(savedUser, command);

		//then
		long postId = response.postId();
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

		assertThat(post.getTitle()).isEqualTo(command.title());
		assertThat(post.getContent()).isEqualTo(command.content());
		assertThat(post.getTags()).hasSize(2)
			.contains(BE, ETC);
	}

	@DisplayName("사용자는 게시글 생성시 태그를 최대 두개만 지정할 수 있다.")
	@Test
	void createPostTagMaxTwo() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);

		PostCreateCommand command = new PostCreateCommand(
			"testtitle",
			"testcontent",
			List.of(BE, ETC, DB)
		);

		//when & then
		assertThatThrownBy(() -> postService.createPost(savedUser, command))
			.isInstanceOf(InvalidTagCountException.class)
			.hasMessage("태그는 2개를 넘게 설정할 수 없습니다.");

	}

	@DisplayName("사용자는 게시글 하나를 조회할 수 있다.")
	@Test
	void getPostDetail() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);

		Post savedPost = postRepository.save(Post.create(
			new Title("제목"),
			new Content("내용"),
			List.of(BE, ETC),
			savedUser
		));

		commentRepository.save(Comment.createWithoutParent(
			"댓글",
			savedUser,
			savedPost
		));

		commentRepository.save(Comment.createWithoutParent(
			"댓글2",
			savedUser,
			savedPost
		));

		commentRepository.save(Comment.createWithoutParent(
			"댓글3",
			savedUser,
			savedPost
		));

		postLikeRepository.save(PostLike.create(user.getId(), savedPost.getId()));

		//when

		PostDetailsResponse response = postService.getPostById(savedPost.getId());

		//then

		assertThat(response.post().content()).isEqualTo("내용");

		assertThat(response.comments()).hasSize(3)
			.extracting("authorName")
			.containsExactly("test", "test", "test");

		assertThat(response.post().likesCount()).isEqualTo(1);

	}

	@DisplayName("사용자는 한번에 게시글 10개를 조회할 수 있다.")
	@Test
	void getPageTest() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);

		for (int i = 0; i < 12; i++) {
			postRepository.save(Post.create(
				new Title("제목"),
				new Content("내용"),
				List.of(BE, ETC),
				savedUser
			));
		}

		//when
		List<PostResponse> response = postService.getAllPost();

		//then
		assertThat(response).hasSize(10);

	}

}
