package org.sopt.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {
		ExceptionCode exceptionCode = ex.getErrorCode();
		ExceptionResponse exceptionResponse = ExceptionResponse.of(exceptionCode);
		return new ResponseEntity<>(exceptionResponse, exceptionCode.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException() {
		ExceptionResponse exceptionResponse = ExceptionResponse.of(ExceptionCode.INVALID_INPUT_VALUE);
		return new ResponseEntity<>(exceptionResponse, ExceptionCode.INVALID_INPUT_VALUE.getStatus());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException() {
		ExceptionResponse exceptionResponse = ExceptionResponse.of(ExceptionCode.NOT_FOUND);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException() {
		ExceptionResponse exceptionResponse = ExceptionResponse.of(ExceptionCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException() {
		ExceptionResponse exceptionResponse = ExceptionResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponse> handleJsonParse(HttpMessageNotReadableException ex) {
		Throwable cause = ex.getMostSpecificCause();

		if (cause instanceof BusinessException businessException) {
			ExceptionCode exceptionCode = businessException.getErrorCode();
			return ResponseEntity
				.status(exceptionCode.getStatus())
				.body(ExceptionResponse.of(exceptionCode));
		}

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ExceptionResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR));
	}
}
