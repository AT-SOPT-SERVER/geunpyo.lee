package org.sopt.comment.service;

import org.sopt.comment.controller.response.CommentResponse;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.exception.CommentLengthException;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.comment.service.exception.CommentNotFoundException;
import org.sopt.comment.service.request.CommentCreateCommand;
import org.sopt.comment.service.request.CommentUpdateCommand;
import org.sopt.global.common.annotation.Auth;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.AccessDeniedException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional
	public CommentResponse createComment(@Auth User user, long postId, Long parentId, CommentCreateCommand command) {
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
		validateContentLength(command.content());

		Comment comment = buildComment(user, post, parentId, command.content());

		Comment savedComment = commentRepository.save(comment);

		return CommentResponse.from(savedComment);
	}

	private Comment buildComment(@Auth User user, Post post, Long parentId, String content) {

		if (parentId == null) {
			return Comment.createWithoutParent(content, user, post);
		}

		Comment parent = commentRepository.findById(parentId).orElseThrow(CommentNotFoundException::new);
		return Comment.createWithParent(content, user, post, parent);
	}

	@Transactional
	public void updateComment(@Auth User user, long commentId, CommentUpdateCommand command) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
		checkAuthentication(user, comment);

		validateContentLength(command.content());

		comment.updateContent(command.content());
	}

	@Transactional
	public void deleteComment(@Auth User user, long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
		checkAuthentication(user, comment);

		commentRepository.delete(comment);
	}

	private void checkAuthentication(@Auth User user, Comment comment) {
		if (!comment.getUser().equals(user)) {
			throw new AccessDeniedException();
		}
	}

	private void validateContentLength(String content) {
		if (content.length() > 300) {
			throw new CommentLengthException();
		}
	}
}
