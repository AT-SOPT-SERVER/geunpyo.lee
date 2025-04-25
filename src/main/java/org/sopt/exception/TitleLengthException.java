package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class TitleLengthException extends BusinessException {
	public TitleLengthException() {
		super(ExceptionCode.TITLE_TOO_LONG);
	}
}
