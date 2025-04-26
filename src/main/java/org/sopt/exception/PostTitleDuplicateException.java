package org.sopt.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class PostTitleDuplicateException extends BusinessException {
	public PostTitleDuplicateException() {
		super(ExceptionCode.DUPLICATE_POST_TITLE);
	}
}
