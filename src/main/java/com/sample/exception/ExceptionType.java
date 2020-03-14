package com.sample.exception;

/**
 * Types of exception in user services. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

public enum ExceptionType {
	USER_NOT_FOUND_EXCEPTION,
	DUPLICATE_USER,
	DUPLICATE_EMAIL_ADDRESS,
	DUPLICATE_PHONE_NUMBER,
	EMAIL_NOT_FOUND_EXCEPTION,
	PHONE_NUMBER_NOT_FOUND_EXCEPTION,
	INVALID_EMAIL_ADDRESS,
	INVALID_PHONE_NUMBER;
}
