package com.calender.assistant.exception;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) 
	{
		StringBuilder sb = new StringBuilder();
	    sb.append(e.getBindingResult().getAllErrors()
	    		 		  	.stream().map(ObjectError::getDefaultMessage)
	    		 		  	.collect(Collectors.joining(", ")));
	    return ResponseEntity.badRequest().body(sb.toString());
	}
	@ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        String errorMessage = "Invalid date format. Please provide the date in the format yyyy-MM-dd.";
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
