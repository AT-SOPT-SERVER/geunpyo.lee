package org.sopt.comment.service;

import org.sopt.comment.controller.response.CommentLikeChangeResponse;
import org.sopt.comment.domain.CommentLike;
import org.sopt.comment.repository.CommentLikeRepository;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {
	private final CommentRepository commentRepository;
	private final CommentLikeRepository commentLikeRepository;

	@Transactional
	public CommentLikeChangeResponse toggleLike(User user, long commentId) {
		return commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId())
			.map(commentLike -> {
				commentLike.toggle();
				return CommentLikeChangeResponse.from(commentLike);
			})
			.orElseGet(() -> {
				CommentLike newLike = CommentLike.create(user.getId(), commentId);
				commentLikeRepository.save(newLike);
				return CommentLikeChangeResponse.from(newLike);
			});
	}
}
