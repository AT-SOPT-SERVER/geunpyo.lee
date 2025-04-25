package org.sopt.global.common.dto;

import org.sopt.global.common.constant.SuccessCode;
import org.springframework.http.HttpStatus;

public class ResponseDto<T> {
	private final String code;
	private final String message;
	private final T data;

	private ResponseDto(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> ResponseDto<T> of(SuccessCode successCode, T data) {
		return new ResponseDto<>(successCode.getCode(), successCode.getMessage(), data);
	}

	public static <T> ResponseDto<T> of(HttpStatus httpStatus, T data) {
		return of(SuccessCode.valueOf(httpStatus), data);
	}

	public static <T> ResponseDto<T> ok(T data) {
		return of(SuccessCode.OK, data);
	}

	public static <T> ResponseDto<T> created(T data) {
		return of(SuccessCode.CREATED, data);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
}
