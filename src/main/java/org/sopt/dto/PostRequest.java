package org.sopt.dto;

import static org.sopt.util.StringLengthUtil.*;

import org.sopt.exception.TitleLengthException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostRequest {
	private static final int MAX_LENGTH = 30;
	private final String title;

	@JsonCreator
	public PostRequest(@JsonProperty("title") String title) {
		validate(title);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	private void validate(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
		}

		int length = getLength(value);
		if (length > MAX_LENGTH) {
			throw new TitleLengthException();
		}
	}
}

