package org.sopt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ExceptionCode;

public class TitleEmptyException extends BusinessException {
	public TitleEmptyException() {
		super(ExceptionCode.EMPTY_TITLE);
	}
}
