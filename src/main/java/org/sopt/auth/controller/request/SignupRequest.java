package org.sopt.auth.controller.request;

public record SignupRequest(
	String email,
	String password,
	String name
) {
}
