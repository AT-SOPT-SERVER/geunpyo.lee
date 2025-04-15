package org.sopt.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostRequest {
	private static final int MAX_LENGTH = 30;
	private static final Pattern GRAPHEME_PATTERN = Pattern.compile("\\X");
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
			throw new IllegalArgumentException(
				String.format("제목은 %d자를 넘길 수 없습니다. (현재: %d자)", MAX_LENGTH, length));
		}
	}

	public int getLength(String text) {
		if (text == null) {
			return 0;
		}
		Matcher matcher = GRAPHEME_PATTERN.matcher(text);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}
}

