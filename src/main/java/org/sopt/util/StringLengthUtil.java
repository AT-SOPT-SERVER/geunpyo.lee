package org.sopt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLengthUtil {
	private static final Pattern GRAPHEME_PATTERN = Pattern.compile("\\X");

	public static int getLength(String text) {
		if (text == null) {
			return 0;
		}
		Matcher matcher = GRAPHEME_PATTERN.matcher(text);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}
}
