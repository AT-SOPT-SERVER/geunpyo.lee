package org.sopt.global.config.exception.constant;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {

	//400
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "c4000", "잘못된 요청입니다."),

	EMPTY_TITLE(HttpStatus.BAD_REQUEST, "c40010", "제목이 비어있습니다"),
	TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "c40020", "제목은 30글자를 넘을 수 없습니다."),

	EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "c40011", "내용이 비어있습니다"),
	CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "c40021", "내용은 1000자를 넘을 수 없습니다."),

	EMPTY_NAME(HttpStatus.BAD_REQUEST, "c40012", "이름이 비어있습니다"),
	NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "c40022", "이름은 7자를 넘을 수 없습니다."),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "c40023", "올바르지 않은 이메일 형식입니다."),
	EMPTY_USER_ID(HttpStatus.BAD_REQUEST, "c40013", "유저ID는 필수입니다."),

	INVALID_TAG(HttpStatus.BAD_REQUEST, "c40024", "올바르지 않은 태그입니다."),

	//403
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "c4031", "작성자만 게시물을 변경할 수 있습니다."),

	//404
	NOT_FOUND(HttpStatus.NOT_FOUND, "c4040", "리소스가 존재하지 않습니다."),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "c4040", "대상이 존재하지 않습니다"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "c4041", "게시글이 존재하지 않습니다."),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "c4042", "사용자가 존재하지 않습니다."),

	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "c4050", "잘못된 HTTP method 요청입니다."),

	//409
	DUPLICATE(HttpStatus.CONFLICT, "c4090", "이미 존재하는 리소스입니다."),
	DUPLICATE_POST_TITLE(HttpStatus.CONFLICT, "c4091", "이미 동일한 내용의 게시물이 있습니다."),

	//429
	POST_CREATION_COOLDOWN(HttpStatus.TOO_MANY_REQUESTS, "c4291", "도배 방지를 위해 잠시 후에 다시 시도해주세요."),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "c5000", "서버 내부 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ExceptionCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
