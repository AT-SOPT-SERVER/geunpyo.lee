package org.sopt.post.controller.response;

import org.sopt.post.domain.Post;

public record PostDetailResponse(
	String title,
	String content,
	String username
) {
	public static PostDetailResponse from(Post post) {
		return new PostDetailResponse(
			post.getTitle(),
			post.getContent(),
			post.getUser().getName()
		);
	}
}
