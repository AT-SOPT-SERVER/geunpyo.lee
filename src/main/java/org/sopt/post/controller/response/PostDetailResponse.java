package org.sopt.post.controller.response;

import java.util.List;

import org.sopt.post.repository.dto.CommentDetailProjection;
import org.sopt.post.repository.dto.PostSummaryProjection;

public record PostDetailResponse(
	String title,
	String content,
	String username,
	List<CommentDetailProjection> comments
) {
	public static PostDetailResponse of(PostSummaryProjection postDetail,
		List<CommentDetailProjection> commentDetails) {

		return new PostDetailResponse(
			postDetail.getTitle(),
			postDetail.getContent(),
			postDetail.getAuthorName(),
			commentDetails
		);
	}
}
