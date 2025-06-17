package org.sopt.post.controller.response;

import java.util.List;

import org.sopt.comment.controller.response.CommentDetail;

public record PostDetailsResponse(
	PostDetail post,
	List<CommentDetail> comments
) {
	public static PostDetailsResponse of(PostDetail postDetail,
		List<CommentDetail> commentDetails) {
		return new PostDetailsResponse(
			postDetail,
			commentDetails
		);
	}
}
