package org.sopt.comment.controller.response;

import org.sopt.comment.domain.CommentLike;

public record CommentLikeChangeResponse(
	long id,
	boolean isActive
) {
	public static CommentLikeChangeResponse from(CommentLike commentLike) {
		return new CommentLikeChangeResponse(
			commentLike.getId(),
			commentLike.isActive()
		);
	}
}
