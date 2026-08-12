package esun.social_media_test.controller.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import esun.social_media_test.dto.ErrorResp;
import esun.social_media_test.exception.DataDuplicateException;
import esun.social_media_test.exception.DataNotFoundException;
import esun.social_media_test.exception.ErrorInputException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {
	/**
	 * 捕捉資料輸入錯誤 (400 Bad Request)
	 */
	@ExceptionHandler(ErrorInputException.class)
	public ResponseEntity<ErrorResp> handleDataInputException(ErrorInputException ex) {
		log.warn("輸入資料驗證失敗: {}", ex.getMessage());

		ErrorResp error = new ErrorResp(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * 捕捉查無資料錯誤 (404 Not Found)
	 */
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResp> handleDataNotFoundException(DataNotFoundException ex) {
		log.warn("查無資料: {}", ex.getMessage());

		ErrorResp error = new ErrorResp(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * 捕捉資料重複錯誤 (409 Conflict)
	 */
	@ExceptionHandler(DataDuplicateException.class)
	public ResponseEntity<ErrorResp> handleDataDuplicateException(DataDuplicateException ex) {
		log.warn("資料重複: {}", ex.getMessage());

		ErrorResp error = new ErrorResp(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	/**
	 * 捕捉未知的系統全域異常 (500 Internal Server Error)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResp> handleGlobalException(Exception ex) {
		log.error("系統發生未預期異常", ex);

		ErrorResp error = new ErrorResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系統忙碌中，請稍後再試！", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}