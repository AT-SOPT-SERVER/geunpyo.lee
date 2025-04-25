package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class RequestCooldownException extends BusinessException {
	public RequestCooldownException() {
		super(ExceptionCode.POST_CREATION_COOLDOWN);
	}
}
