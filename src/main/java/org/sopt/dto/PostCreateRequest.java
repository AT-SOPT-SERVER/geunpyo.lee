package org.sopt.dto;

import static org.sopt.util.TitleFormatValidateUtil.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PostCreateRequest(String title) {

	@JsonCreator
	public PostCreateRequest(@JsonProperty("title") String title) {
		validate(title);
		this.title = title;
	}
}

