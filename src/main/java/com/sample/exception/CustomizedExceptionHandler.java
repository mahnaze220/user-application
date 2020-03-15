package com.sample.exception;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sample.exception.UserServiceException.DuplicateEmailException;
import com.sample.exception.UserServiceException.DuplicatePhoneNumberException;
import com.sample.exception.UserServiceException.DuplicateUserException;
import com.sample.exception.UserServiceException.EmailNotFoundException;
import com.sample.exception.UserServiceException.InvalidEmailAddressException;
import com.sample.exception.UserServiceException.InvalidPhoneNumberException;
import com.sample.exception.UserServiceException.PhoneNumberNotFoundException;
import com.sample.exception.UserServiceException.UserNotFoundException;

/**
 * Exception handler for user services to customize response status codes. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
	
	@ExceptionHandler(PhoneNumberNotFoundException.class)
    public void handlePhoneNumberNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
	
	@ExceptionHandler(EmailNotFoundException.class)
    public void handleEmailNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
	
	@ExceptionHandler(DuplicateUserException.class)
    public void handleDuplicateUserException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(DuplicatePhoneNumberException.class)
    public void handleDuplicatePhoneNumberException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(DuplicateEmailException.class)
    public void handleDuplicateEmailException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(InvalidEmailAddressException.class)
    public void handleInvalidEmailAddressException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(InvalidPhoneNumberException.class)
    public void handleInvalidPhoneNumberException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("timestamp", new Date());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }
}
