package org.sopt.util;

import static org.sopt.util.StringLengthUtil.*;

import org.sopt.exception.ContentEmptyException;
import org.sopt.exception.ContentLengthException;

public class ContentFormatValidateUtil {
	private static final int CONTENT_MAX_LENGTH = 1000;

	public static void validateContentFormat(String value) {
		if (value == null || value.isBlank()) {
			throw new ContentEmptyException();
		}

		int length = getLength(value);
		if (length > CONTENT_MAX_LENGTH) {
			throw new ContentLengthException();
		}
	}
}
