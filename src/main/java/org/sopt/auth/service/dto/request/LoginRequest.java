package org.sopt.auth.service.dto.request;

public record LoginRequest(
	String email,
	String password
) {
}
