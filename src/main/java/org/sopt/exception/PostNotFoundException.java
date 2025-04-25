package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class PostNotFoundException extends BusinessException {
	public PostNotFoundException() {
		super(ExceptionCode.POST_NOT_FOUND);
	}
}

