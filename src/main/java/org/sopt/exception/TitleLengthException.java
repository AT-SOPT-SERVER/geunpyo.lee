package org.sopt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ExceptionCode;

public class TitleLengthException extends BusinessException {
	public TitleLengthException() {
		super(ExceptionCode.TITLE_TOO_LONG);
	}
}
