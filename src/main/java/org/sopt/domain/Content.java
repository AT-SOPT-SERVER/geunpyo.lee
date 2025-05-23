package org.sopt.domain;

import static org.sopt.util.ContentFormatValidateUtil.*;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Content {
	@Column(name = "content", nullable = false)
	private String value;

	public Content(
		String value) {
		validateContentFormat(value);
		this.value = value;
	}

	@Column(name = "content", nullable = false)
	public String value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (Content)obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Content[" +
			"value=" + value + ']';
	}

}

