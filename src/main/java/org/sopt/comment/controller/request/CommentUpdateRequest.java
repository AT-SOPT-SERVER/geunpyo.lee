package org.sopt.comment.controller.request;

import org.sopt.comment.service.request.CommentUpdateCommand;

public record CommentUpdateRequest(
	String content
) {
	public CommentUpdateCommand toCommand() {
		return new CommentUpdateCommand(this.content);
	}
}
