package org.sopt.user.controller.response;

public record UserResponse(
	long userId,
	String name,
	String email
) {
	public static UserResponse of(long userId, String name, String email) {
		return new UserResponse(userId, name, email);
	}
}
