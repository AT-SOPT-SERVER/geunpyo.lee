package org.sopt.auth.controller.response;

import org.sopt.user.domain.User;

public record UserInfo(
	String name
) {
	public static UserInfo fromEntity(User user) {
		return new UserInfo(user.getName());
	}
}
