package org.sopt.auth.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class ConflictEmailException extends BusinessException {
	public ConflictEmailException() {
		super(ExceptionCode.INVALID_EMAIL_FORMAT);
	}
}
