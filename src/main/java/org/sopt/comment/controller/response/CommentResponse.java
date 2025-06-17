package org.sopt.comment.controller.response;

import org.sopt.comment.domain.Comment;

public record CommentResponse(
	long commentId,
	String content,
	String userName
) {
	public static CommentResponse from(Comment comment) {
		return new CommentResponse(
			comment.getId(),
			comment.getContent(),
			comment.getUser().getName()
		);
	}
}
