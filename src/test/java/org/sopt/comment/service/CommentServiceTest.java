package org.sopt.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.post.domain.constant.Tag.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.comment.controller.response.CommentResponse;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.comment.service.exception.CommentNotFoundException;
import org.sopt.comment.service.request.CommentCreateCommand;
import org.sopt.comment.service.request.CommentUpdateCommand;
import org.sopt.post.domain.Content;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Title;
import org.sopt.post.exception.AccessDeniedException;
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
			"testComment"
		);

		//when

		CommentResponse response = commentService.createComment(savedUser2.getId(), savedPost.getId(), null, command);

		//then
		Comment comment = commentRepository.findById(response.commentId()).get();

		assertThat(comment.getUser().getId()).isEqualTo(savedUser2.getId());
		assertThat(comment.getPost().getId()).isEqualTo(savedPost.getId());
		assertThat(comment.getContent()).isEqualTo(command.content());
		assertThat(comment.getParent()).isNull();
		assertThat(comment.getLikes()).isEqualTo(1);

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
			"child"
		);

		//when
		CommentResponse response = commentService.createComment(savedUser2.getId(), savedPost.getId(),
			savedPost.getId(), command);

		//then
		Comment comment = commentRepository.findById(response.commentId()).get();

		assertThat(comment.getUser().getId()).isEqualTo(savedUser2.getId());
		assertThat(comment.getPost().getId()).isEqualTo(savedPost.getId());
		assertThat(comment.getParent().getId()).isEqualTo(savedParent.getId());
		assertThat(comment.getContent()).isEqualTo(command.content());
		assertThat(comment.getLikes()).isEqualTo(1);

	}

	@DisplayName("사용자는 댓글을 수정할 수 있다.")
	@Test
	void update() {
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
		Comment comment = commentRepository.save(Comment.createWithoutParent("testComment", savedUser2, savedPost));

		CommentUpdateCommand command = new CommentUpdateCommand("updateComment");
		//when
		commentService.updateComment(savedUser2.getId(), comment.getId(), command);

		//then
		Comment resultComment = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);

		assertThat(resultComment.getContent()).isEqualTo("updateComment");

	}

	@DisplayName("사용자는 다른 사람의 댓글을 수정할 수 없다.")
	@Test
	void updateAnotherUserComment() {
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
		Comment comment = commentRepository.save(Comment.createWithoutParent("testComment", savedUser2, savedPost));

		CommentUpdateCommand command = new CommentUpdateCommand("updateComment");

		//when & then
		assertThatThrownBy(() -> commentService.updateComment(savedUser.getId(), comment.getId(), command))
			.isInstanceOf(AccessDeniedException.class);

	}

	@DisplayName("사용자는 댓글을 삭제할 수 있다.")
	@Test
	void deleteComment() {
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
		Comment comment = commentRepository.save(Comment.createWithoutParent("testComment", savedUser2, savedPost));

		//when
		commentService.deleteComment(savedUser2.getId(), comment.getId());

		//then
		Comment resultComment = commentRepository.findById(comment.getId()).orElse(null);
		assertThat(resultComment).isNull();
	}

}
