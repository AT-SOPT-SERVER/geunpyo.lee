package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class NameEmptyException extends BusinessException {
	public NameEmptyException() {
		super(ExceptionCode.EMPTY_NAME);
	}
}
