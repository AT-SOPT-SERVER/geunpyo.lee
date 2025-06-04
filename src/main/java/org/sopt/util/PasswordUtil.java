package org.sopt.util;

public interface PasswordUtil {

	String encode(String password);

	boolean matches(String rawPassword, String encodedPassword);
}
