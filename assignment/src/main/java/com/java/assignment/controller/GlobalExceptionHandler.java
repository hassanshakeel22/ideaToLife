package com.java.assignment.controller;

import com.java.assignment.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * Exception handler for handling validation errors.
	 *
	 * @param ex MethodArgumentNotValidException thrown during validation
	 * @return ResponseEntity containing a formatted validation error message
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		String errorMessage = "Validation error(s): ";

		// Build a comma-separated list of validation errors
		errorMessage += bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
				.reduce((msg1, msg2) -> msg1 + ", " + msg2).orElse("Unknown error");

		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Exception handler for handling validation errors.
	 *
	 * @param ex NotFoundException thrown when result not found
	 * @return ResponseEntity containing an error message and status
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(Throwable.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public void handleUnknownException(Throwable ex) {
//	}
}
