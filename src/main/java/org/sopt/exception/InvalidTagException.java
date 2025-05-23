package org.sopt.exception;

import static org.sopt.global.config.exception.constant.ExceptionCode.*;

import org.sopt.global.config.exception.BusinessException;

public class InvalidTagException extends BusinessException {
	public InvalidTagException() {
		super(INVALID_TAG);
	}
}
