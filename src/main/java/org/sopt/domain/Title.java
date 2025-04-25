package org.sopt.domain;

import static org.sopt.util.TitleFormatValidateUtil.*;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Title {

	@Column(nullable = false)
	private String content;

	public Title(String content) {
		validate(content);
		this.content = content;
	}

	public Title() {
	}

	public String getContent() {
		return content;
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
