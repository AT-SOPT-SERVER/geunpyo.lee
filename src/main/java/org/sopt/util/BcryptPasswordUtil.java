package org.sopt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordUtil implements PasswordUtil {
	private final BCryptPasswordEncoder encoder;

	public BcryptPasswordUtil() {
		this.encoder = new BCryptPasswordEncoder(12);
	}

	@Override
	public String encode(String password) {
		return encoder.encode(password);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}
}
