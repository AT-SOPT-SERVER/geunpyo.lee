package org.sopt.comment.service.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class CommentNotFoundException extends BusinessException {
	public CommentNotFoundException() {
		super(ExceptionCode.COMMENT_NOT_FOUND);
	}
}
