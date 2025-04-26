package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class TitleEmptyException extends BusinessException {
	public TitleEmptyException() {
		super(ExceptionCode.EMPTY_TITLE);
	}
}
