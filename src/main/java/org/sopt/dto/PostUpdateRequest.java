package org.sopt.dto;

import static org.sopt.util.TitleFormatValidateUtil.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PostUpdateRequest(String title) {
	@JsonCreator
	public PostUpdateRequest(@JsonProperty("title") String title) {
		validate(title);
		this.title = title;
	}

}
