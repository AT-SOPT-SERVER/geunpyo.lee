package org.sopt.post.domain.constant;

import org.sopt.user.exception.InvalidTagException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Tag {
	BE, INFRA, DB, ETC;

	@JsonCreator
	public static Tag fromString(String value) {
		for (Tag tag : Tag.values()) {
			if (tag.name().equalsIgnoreCase(value)) {
				return tag;
			}
		}
		throw new InvalidTagException();
	}
}
