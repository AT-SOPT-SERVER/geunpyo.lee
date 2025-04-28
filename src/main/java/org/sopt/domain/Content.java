package org.sopt.domain;

import static org.sopt.util.ContentFormatValidateUtil.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Content(
	@Column(nullable = false)
	String value
) {
	public Content {
		validateContentFormat(value);
	}
}

