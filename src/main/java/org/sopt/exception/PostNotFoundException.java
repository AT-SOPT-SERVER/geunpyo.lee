package org.sopt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ExceptionCode;

public class PostNotFoundException extends BusinessException {

	public PostNotFoundException() {
		super(ExceptionCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다.");
	}
}

