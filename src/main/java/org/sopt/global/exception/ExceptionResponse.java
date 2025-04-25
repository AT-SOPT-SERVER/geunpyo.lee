package org.sopt.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(String code, String message) {

	public static ExceptionResponse of(ExceptionCode exceptionCode) {
		return new ExceptionResponse(
			exceptionCode.getCode(),
			exceptionCode.getMessage()
		);
	}
}
