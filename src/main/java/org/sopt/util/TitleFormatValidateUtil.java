package org.sopt.util;

import static org.sopt.util.StringLengthUtil.*;

import org.sopt.exception.TitleEmptyException;
import org.sopt.exception.TitleLengthException;

public class TitleFormatValidateUtil {
	private static final int MAX_LENGTH = 30;

	public static void validateTitleFormat(String value) {
		if (value == null || value.isBlank()) {
			throw new TitleEmptyException();
		}

		int length = getLength(value);
		if (length > MAX_LENGTH) {
			throw new TitleLengthException();
		}
	}
}
