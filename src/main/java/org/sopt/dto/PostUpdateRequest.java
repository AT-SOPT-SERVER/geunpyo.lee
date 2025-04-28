package org.sopt.dto;

import static org.sopt.util.TitleFormatValidateUtil.*;

public record PostUpdateRequest(String title, String content) {

	public PostUpdateRequest {
		validateTitleFormat(title);
	}
}
