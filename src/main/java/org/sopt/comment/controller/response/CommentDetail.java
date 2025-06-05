package org.sopt.comment.controller.response;

import org.sopt.comment.domain.Comment;

import lombok.Builder;

@Builder
public record CommentDetail(
	String content,
	String authorName,
	long likes
) {
	public static CommentDetail from(Comment comment) {
		return CommentDetail.builder()
			.content(comment.getContent())
			.authorName(comment.getUser().getName())
			.likes(comment.getLikes())
			.build();
	}
}
