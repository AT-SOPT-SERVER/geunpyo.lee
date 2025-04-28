package org.sopt.dto;

public record UserResponse(
	String name,
	String email
) {
	public static UserResponse of(String name, String email) {
		return new UserResponse(name, email);
	}
}
