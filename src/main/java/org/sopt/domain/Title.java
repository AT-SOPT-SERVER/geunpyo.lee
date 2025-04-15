package org.sopt.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Title {
	private static final int MAX_LENGTH = 30;
	private static final Pattern GRAPHEME_PATTERN = Pattern.compile("\\X");

	@Column(nullable = false)
	private String content;

	public Title(String content) {
		validateLength(content);
		this.content = content;
	}

	public Title() {
	}

	public String getContent() {
		return content;
	}

	private void validateLength(String value) {
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
