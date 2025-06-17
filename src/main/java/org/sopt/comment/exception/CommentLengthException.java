package org.sopt.comment.exception;

import org.sopt.global.config.exception.BusinessException;
import org.sopt.global.config.exception.constant.ExceptionCode;

public class CommentLengthException extends BusinessException {
	public CommentLengthException() {
		super(ExceptionCode.COMMENT_TOO_LONG);
	}
}
