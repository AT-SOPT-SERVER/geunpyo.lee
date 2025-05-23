package org.sopt.domain.constant;

import org.sopt.exception.InvalidTagException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Tag {
	BE, INFRA, DB;

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
