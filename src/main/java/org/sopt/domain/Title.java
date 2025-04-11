package org.sopt.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Title {
    private static final int MAX_LENGTH = 30;
    private final String value;
    private final Pattern graphemePattern = Pattern.compile("\\X");
    private final Matcher graphemeMatcher = graphemePattern.matcher("");

    public Title(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        }

        int length = getLength(value);
        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("제목은 %d자를 넘길 수 없습니다. (현재: %d자)", MAX_LENGTH, length));
        }
    }

    public String getValue() {
        return value;
    }

    public int getLength(String text) {
        if (text == null) {
            return 0;
        }
        graphemeMatcher.reset(text);
        int count = 0;
        while (graphemeMatcher.find()) {
            count++;
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
