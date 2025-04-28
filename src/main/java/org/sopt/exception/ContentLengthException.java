package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class ContentLengthException extends BusinessException {
	public ContentLengthException() {
		super(ExceptionCode.CONTENT_TOO_LONG);
	}
}
