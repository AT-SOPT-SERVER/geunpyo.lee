package org.sopt.comment.controller.request;

import org.sopt.comment.service.request.CommentCreateCommand;

public record CommentCreateRequest(String content) {
	public CommentCreateCommand toCommand() {
		return new CommentCreateCommand(this.content);
	}
}
