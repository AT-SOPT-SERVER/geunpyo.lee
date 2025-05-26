package org.sopt.post.controller.request;

import static org.sopt.util.ContentFormatValidateUtil.*;
import static org.sopt.util.TitleFormatValidateUtil.*;

import java.util.List;

import org.sopt.post.domain.constant.Tag;

public record PostCreateRequest(String title, String content, List<Tag> tags) {

	public PostCreateRequest {
		validateTitleFormat(title);
		validateContentFormat(content);
	}
}

