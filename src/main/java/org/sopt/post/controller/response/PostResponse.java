package org.sopt.post.controller.response;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;

public record PostResponse(
	long postId,
	String title,
	String username,
	String content,
	List<Tag> tags
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getUser().getName(),
			post.getContent(),
			post.getTags()
		);
	}
}
