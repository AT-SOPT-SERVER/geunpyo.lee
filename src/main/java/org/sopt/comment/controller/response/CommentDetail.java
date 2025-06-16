package org.sopt.comment.controller.response;

import java.time.LocalDateTime;

import org.sopt.post.repository.dto.CommentDetailProjection;

import lombok.Builder;

@Builder
public record CommentDetail(
	Long id,
	String content,
	LocalDateTime createdAt,
	String authorName,
	long likesCount
) {
	public static CommentDetail from(CommentDetailProjection projection) {
		return CommentDetail.builder()
			.id(projection.getId())
			.content(projection.getContent())
			.createdAt(projection.getCreatedAt())
			.authorName(projection.getAuthorName())
			.likesCount(projection.getLikesCount())
			.build();
	}
}
