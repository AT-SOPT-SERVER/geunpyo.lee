package org.sopt.exception;

import static org.sopt.global.config.exception.constant.ExceptionCode.*;

import org.sopt.global.config.exception.BusinessException;

public class AccessDeniedException extends BusinessException {
	public AccessDeniedException() {
		super(ACCESS_DENIED);
	}
}
