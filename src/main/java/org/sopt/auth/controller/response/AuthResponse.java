package org.sopt.auth.controller.response;

import lombok.Builder;

@Builder
public record AuthResponse(
	String accessToken,
	UserInfo user
) {
}
