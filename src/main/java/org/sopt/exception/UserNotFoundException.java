package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class UserNotFoundException extends BusinessException {
	public UserNotFoundException() {
		super(ExceptionCode.USER_NOT_FOUND);
	}
}
