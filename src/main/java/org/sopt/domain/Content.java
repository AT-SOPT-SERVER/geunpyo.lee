package org.sopt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Content(
	@Column(nullable = false)
	String value
) {
}
