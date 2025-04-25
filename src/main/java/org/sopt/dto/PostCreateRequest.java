package org.sopt.dto;

import static org.sopt.util.StringLengthUtil.*;

import org.sopt.exception.TitleEmptyException;
import org.sopt.exception.TitleLengthException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PostCreateRequest(String title) {
	private static final int MAX_LENGTH = 30;

	@JsonCreator
	public PostCreateRequest(@JsonProperty("title") String title) {
		validate(title);
		this.title = title;
	}

	private void validate(String value) {
		if (value == null || value.isBlank()) {
			throw new TitleEmptyException();
		}

		int length = getLength(value);
		if (length > MAX_LENGTH) {
			throw new TitleLengthException();
		}
	}
}

