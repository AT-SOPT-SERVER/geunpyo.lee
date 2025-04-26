package org.sopt.global.config.exception;

import org.sopt.global.config.exception.constant.ExceptionCode;

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
