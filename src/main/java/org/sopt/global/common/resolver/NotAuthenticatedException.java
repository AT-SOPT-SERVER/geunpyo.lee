package org.sopt.global.common.resolver;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class NotAuthenticatedException extends BusinessException {
	public NotAuthenticatedException() {
		super(ExceptionCode.NOT_AUTHENTICATED);
	}
}
