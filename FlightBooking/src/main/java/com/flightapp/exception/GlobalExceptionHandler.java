package com.flightapp.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightapp.model.ResponseForException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UserDefinedException.class)
	public ResponseEntity<Object> BookingFailedException(UserDefinedException ex) {

		return new ResponseEntity<>(new ResponseForException(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ResponseForException> invalidTokenException(InvalidTokenException invalidTokenException) {
		
		return new ResponseEntity<>(
				new ResponseForException(invalidTokenException.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED),
				HttpStatus.UNAUTHORIZED);
	}
}
