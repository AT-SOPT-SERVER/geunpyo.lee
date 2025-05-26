package org.sopt.user.controller.request;

import static org.sopt.util.EmailValidator.*;

public record UserCreateRequest(
	String name,
	String email
) {
	public UserCreateRequest {
		validate(name, email);
	}
}
