package org.sopt.post.controller.response;

import java.util.List;

import org.sopt.post.repository.dto.CommentDetailDto;
import org.sopt.post.repository.dto.PostSummaryDto;

public record PostDetailResponse(
	String title,
	String content,
	String username,
	List<CommentDetailDto> comments
) {
	public static PostDetailResponse of(PostSummaryDto postDetail, List<CommentDetailDto> commentDetails) {

		return new PostDetailResponse(
			postDetail.getTitle(),
			postDetail.getContent(),
			postDetail.getAuthorName(),
			commentDetails
		);
	}
}
