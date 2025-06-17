package org.sopt.post.controller.response;

import org.sopt.post.repository.dto.PostSummaryProjection;

import lombok.Builder;

@Builder
public record PostDetail(
	String title,
	String content,
	String username,
	long likesCount
) {
	public static PostDetail from(PostSummaryProjection postSummaryProjection) {
		return PostDetail.builder()
			.title(postSummaryProjection.getTitle())
			.content(postSummaryProjection.getContent())
			.username(postSummaryProjection.getAuthorName())
			.likesCount(postSummaryProjection.getLikesCount())
			.build();
	}
}
