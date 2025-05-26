package org.sopt.comment.service;

import org.sopt.comment.controller.response.CommentResponse;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.comment.service.exception.CommentNotFoundException;
import org.sopt.comment.service.request.CommentCreateCommand;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public CommentService(CommentRepository commentRepository, UserRepository userRepository,
		PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@Transactional
	public CommentResponse createComment(long userId, long postId, Long parentId, CommentCreateCommand command) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

		Comment comment = buildComment(user, post, parentId, command.content());

		Comment savedComment = commentRepository.save(comment);

		return CommentResponse.from(savedComment);
	}

	private Comment buildComment(User user, Post post, Long parentId, String content) {

		if (parentId == null) {
			return Comment.createWithoutParent(content, user, post);
		}

		Comment parent = commentRepository.findById(parentId).orElseThrow(CommentNotFoundException::new);
		return Comment.createWithParent(content, user, post, parent);
	}
}
