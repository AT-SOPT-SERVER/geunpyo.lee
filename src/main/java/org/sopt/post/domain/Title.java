package org.sopt.post.domain;

import static org.sopt.util.TitleFormatValidateUtil.*;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {
	@Column(name = "title", nullable = false)
	private String content;

	public Title(
		String content) {
		validateTitleFormat(content);
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}

	@Column(name = "title", nullable = false)
	public String content() {
		return content;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (Title)obj;
		return Objects.equals(this.content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content);
	}

}
