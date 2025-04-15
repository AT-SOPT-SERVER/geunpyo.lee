package org.sopt.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

@Embeddable
public class Title {
	private static final int MAX_LENGTH = 30;
	private static final Pattern GRAPHEME_PATTERN = Pattern.compile("\\X");

	private String content;

	public Title(String value) {
		validate(value);
		this.content = value;
	}

	public Title() {
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

	public String getContent() {
		return content;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Title title = (Title)o;
		return Objects.equals(content, title.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content);
	}

	@Override
	public String toString() {
		return content;
	}
}
