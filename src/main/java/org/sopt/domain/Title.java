package org.sopt.domain;

import static org.sopt.util.TitleFormatValidateUtil.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Title(
	@Column(nullable = false)
	String content
) {
	public Title {
		validateTitleFormat(content);
	}

	@Override
	public String toString() {
		return content;
	}
}
