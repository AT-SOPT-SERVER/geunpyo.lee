package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class ContentEmptyException extends BusinessException {
	public ContentEmptyException() {
		super(ExceptionCode.EMPTY_CONTENT);
	}
}
