package org.sopt.auth.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class PasswordNotMatchedException extends BusinessException {
	public PasswordNotMatchedException() {
		super(ExceptionCode.PASSWORD_NOT_MATCHED);
	}
}
