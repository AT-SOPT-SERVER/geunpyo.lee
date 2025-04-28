package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class InvalidEmailFormatException extends BusinessException {
	public InvalidEmailFormatException() {
		super(ExceptionCode.INVALID_EMAIL_FORMAT);
	}
}
