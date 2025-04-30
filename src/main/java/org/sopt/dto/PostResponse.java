package org.sopt.dto;

import org.sopt.domain.Post;

public record PostResponse(
	String title,
	String username
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
			post.getTitle(),
			post.getUser().getName()
		);
	}
}
