package org.sopt.post.controller.response;

import java.util.List;

import org.sopt.comment.controller.response.CommentDetail;
import org.sopt.comment.domain.Comment;
import org.sopt.post.domain.Post;

public record PostDetailResponse(
	String title,
	String content,
	String username,
	List<CommentDetail> comments
) {
	public static PostDetailResponse of(Post post, List<Comment> comments) {
		List<CommentDetail> commentInfos = comments.stream()
			.map(CommentDetail::from)
			.toList();
		
		return new PostDetailResponse(
			post.getTitle(),
			post.getContent(),
			post.getUser().getName(),
			commentInfos
		);
	}
}
