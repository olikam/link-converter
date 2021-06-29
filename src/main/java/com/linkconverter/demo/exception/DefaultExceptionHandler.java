package com.linkconverter.demo.exception;

import com.linkconverter.demo.model.DeepLinkRequest;
import com.linkconverter.demo.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		String response = isWebUrlValidator(e) ? UrlUtil.DEEP_LINK_HOME : UrlUtil.WEB_URL_HOME;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		log.error(e.getMessage());
		return new ResponseEntity<>("There is an unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean isWebUrlValidator(MethodArgumentNotValidException e) {
		return e.getParameter().getParameterType().isAssignableFrom(DeepLinkRequest.class);
	}
}