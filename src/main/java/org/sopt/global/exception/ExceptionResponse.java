package org.sopt.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
	private final String code;
	private final String message;

	public ExceptionResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ExceptionResponse of(ExceptionCode exceptionCode) {
		return new ExceptionResponse(
			exceptionCode.getCode(),
			exceptionCode.getMessage()
		);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
