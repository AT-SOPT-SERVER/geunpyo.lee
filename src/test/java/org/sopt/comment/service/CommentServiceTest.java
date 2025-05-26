package org.sopt.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.post.domain.constant.Tag.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.comment.service.request.CommentCreateCommand;
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

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CommentServiceTest {
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@DisplayName("사용자는 댓글을 작성할 수 있다.")
	@Test
	void createCommentNoParent() {
		//given
		User user = User.create("test", "test@gmail.com");
		User user2 = User.create("test2", "test@gmail.com");
		User savedUser = userRepository.save(user);
		User savedUser2 = userRepository.save(user2);

		Post post = Post.create(
			new Title("testtitle"),
			new Content("testContent"),
			List.of(BE, ETC),
			savedUser
		);

		Post savedPost = postRepository.save(post);

		CommentCreateCommand command = new CommentCreateCommand(
			null,
			post.getId(),
			"testComment"
		);

		//when

		Comment comment = commentService.createComment(savedUser2.getId(), command);

		//then
		assertThat(comment.getUser().getId()).isEqualTo(savedUser2.getId());
		assertThat(comment.getPost().getId()).isEqualTo(savedPost.getId());
		assertThat(comment.getParent()).isNull();

	}

	@DisplayName("사용자는 대댓글을 작성할 수 있다.")
	@Test
	void createWithParent() {
		//given
		User user = User.create("test", "test@gmail.com");
		User user2 = User.create("test2", "test@gmail.com");
		User savedUser = userRepository.save(user);
		User savedUser2 = userRepository.save(user2);

		Post post = Post.create(
			new Title("testtitle"),
			new Content("testContent"),
			List.of(BE, ETC),
			savedUser
		);

		Post savedPost = postRepository.save(post);

		Comment parent = Comment.createWithoutParent(
			"testContent",
			savedUser,
			post
		);

		Comment savedParent = commentRepository.save(parent);

		CommentCreateCommand command = new CommentCreateCommand(
			savedParent.getId(),
			post.getId(),
			"child"
		);

		//when

		Comment comment = commentService.createComment(savedUser2.getId(), command);

		//then
		assertThat(comment.getUser().getId()).isEqualTo(savedUser2.getId());
		assertThat(comment.getPost().getId()).isEqualTo(savedPost.getId());
		assertThat(comment.getParent().getId()).isEqualTo(savedParent.getId());

	}

}
