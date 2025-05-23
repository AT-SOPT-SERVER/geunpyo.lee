package org.sopt.exception;

import static org.sopt.global.config.exception.constant.ExceptionCode.*;

import org.sopt.global.config.exception.BusinessException;

public class NameLengthException extends BusinessException {
	public NameLengthException() {
		super(NAME_TOO_LONG);
	}
}
