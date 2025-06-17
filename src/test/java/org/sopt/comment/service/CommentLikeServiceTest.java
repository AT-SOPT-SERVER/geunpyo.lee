package org.sopt.comment.service;

import static org.sopt.post.domain.constant.Tag.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.comment.controller.response.CommentLikeChangeResponse;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.post.domain.Content;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Title;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CommentLikeServiceTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CommentLikeService commentLikeService;

	@DisplayName("사용자는 좋아요를 토글할 수 있다. ")
	@Test
	void testInitialToggle() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User user2 = User.create("test2", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);
		User savedUser2 = userRepository.save(user2);

		Post post = Post.create(
			new Title("testtitle"),
			new Content("testContent"),
			List.of(BE, ETC),
			savedUser
		);

		Post savedPost = postRepository.save(post);
		Comment comment = commentRepository.save(Comment.createWithoutParent("testComment", savedUser2, savedPost));
		//when

		CommentLikeChangeResponse response = commentLikeService.toggleLike(user, comment.getId());

		//then
		Assertions.assertThat(response.isActive()).isEqualTo(true);

	}

	@DisplayName("사용자가 이미 좋아요를 눌렀다면 취소상태로 변경한다. ")
	@Test
	void testSecondaryToggle() {
		//given
		User user = User.create("test", "test@gmail.com", "test");
		User user2 = User.create("test2", "test@gmail.com", "test");
		User savedUser = userRepository.save(user);
		User savedUser2 = userRepository.save(user2);

		Post post = Post.create(
			new Title("testtitle"),
			new Content("testContent"),
			List.of(BE, ETC),
			savedUser
		);

		Post savedPost = postRepository.save(post);
		Comment comment = commentRepository.save(Comment.createWithoutParent("testComment", savedUser2, savedPost));
		commentLikeService.toggleLike(user, comment.getId());
		//when

		CommentLikeChangeResponse response = commentLikeService.toggleLike(user, comment.getId());

		//then
		Assertions.assertThat(response.isActive()).isEqualTo(false);

	}

}