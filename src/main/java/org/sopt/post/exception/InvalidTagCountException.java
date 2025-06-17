package org.sopt.post.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class InvalidTagCountException extends BusinessException {
	public InvalidTagCountException() {
		super(ExceptionCode.INVALID_TAG_COUNT);
	}
}
