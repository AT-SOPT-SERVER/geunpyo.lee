package org.sopt.comment.service.request;

public record CommentCreateCommand(
	Long parentId,
	long postId,
	String content
) {
}
