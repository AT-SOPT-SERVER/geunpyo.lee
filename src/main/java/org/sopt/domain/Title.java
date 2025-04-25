package org.sopt.domain;

import static org.sopt.util.StringLengthUtil.*;

import java.util.Objects;

import org.sopt.exception.TitleLengthException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Title {
	private static final int MAX_LENGTH = 30;

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
			throw new TitleLengthException();
		}
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
