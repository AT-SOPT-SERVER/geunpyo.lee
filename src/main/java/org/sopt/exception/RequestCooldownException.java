package org.sopt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ExceptionCode;

public class RequestCooldownException extends BusinessException {
	public RequestCooldownException() {
		super(ExceptionCode.POST_CREATION_COOLDOWN);
	}
}
