package org.sopt.dto;

import static org.sopt.util.TitleFormatValidateUtil.*;

public record PostCreateRequest(String title, String content) {

	public PostCreateRequest {
		validateTitleFormat(title);
	}
}

