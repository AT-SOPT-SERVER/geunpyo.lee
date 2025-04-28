package org.sopt.dto;

import static org.sopt.util.TitleFormatValidateUtil.*;

import org.sopt.domain.constant.Tag;

public record PostCreateRequest(String title, String content, Tag tag) {

	public PostCreateRequest {
		validateTitleFormat(title);
	}
}

