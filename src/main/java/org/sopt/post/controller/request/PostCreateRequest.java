package org.sopt.post.controller.request;

import static org.sopt.util.ContentFormatValidateUtil.*;
import static org.sopt.util.TitleFormatValidateUtil.*;

import java.util.List;

import org.sopt.post.domain.constant.Tag;
import org.sopt.post.service.request.PostCreateCommand;

public record PostCreateRequest(String title, String content, List<Tag> tags) {

	public PostCreateRequest {
		validateTitleFormat(title);
		validateContentFormat(content);
	}

	public PostCreateCommand toCommand() {
		return new PostCreateCommand(
			this.title,
			this.content,
			this.tags
		);
	}
}

