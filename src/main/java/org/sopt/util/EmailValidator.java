package org.sopt.util;

import static org.sopt.util.StringLengthUtil.*;

import org.sopt.exception.InvalidEmailFormatException;
import org.sopt.exception.NameEmptyException;
import org.sopt.exception.NameLengthException;

public class EmailValidator {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private static final int USER_NAME_MAX = 10;

	public static void validate(String name, String email) {
		if (email == null || email.isBlank()) {
			throw new NameEmptyException();
		}

		if (!email.matches(EMAIL_REGEX)) {
			throw new InvalidEmailFormatException();
		}

		int length = getLength(name);

		if (length > USER_NAME_MAX) {
			throw new NameLengthException();
		}
	}
}

