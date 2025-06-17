package org.sopt.post.controller.response;

import org.sopt.post.domain.PostLike;

public record PostLikeChangeResponse(
	long id,
	boolean isActive
) {
	public static PostLikeChangeResponse from(PostLike postLike) {
		return new PostLikeChangeResponse(
			postLike.getId(),
			postLike.isActive()
		);
	}
}
