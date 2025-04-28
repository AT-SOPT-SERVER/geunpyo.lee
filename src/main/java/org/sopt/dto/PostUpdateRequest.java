package org.sopt.dto;

import static org.sopt.util.ContentFormatValidateUtil.*;
import static org.sopt.util.TitleFormatValidateUtil.*;

import org.sopt.domain.constant.Tag;

public record PostUpdateRequest(String title, String content, Tag tag) {

	public PostUpdateRequest {
		validateTitleFormat(title);
		validateContentFormat(content);
	}
}
