package org.sopt.global.exception;

public class BusinessException extends RuntimeException {

	private final ExceptionCode exceptionCode;

	public BusinessException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public BusinessException(ExceptionCode exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public ExceptionCode getErrorCode() {
		return exceptionCode;
	}
}