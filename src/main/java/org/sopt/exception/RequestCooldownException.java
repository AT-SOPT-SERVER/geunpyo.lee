package org.sopt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ExceptionCode;

public class RequestCooldownException extends BusinessException {
	private static final String COOL_TIME_FORMAT = "도배 방지를 위해 %d초 후에 다시 시도해주세요.";

	public RequestCooldownException(long remainingSeconds) {
		super(ExceptionCode.POST_CREATION_COOLDOWN, String.format(COOL_TIME_FORMAT, remainingSeconds));
	}
}
