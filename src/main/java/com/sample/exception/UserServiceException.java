package com.sample.exception;

import org.springframework.stereotype.Component;

/**
 * This class creates related exceptions for user services based on the exception type. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Component
public class UserServiceException extends Exception{

	public static RuntimeException throwException(String messageTemplate, String... args) {
		return new RuntimeException(messageTemplate);
	}

	public static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}
	
	public static class PhoneNumberNotFoundException extends RuntimeException {
		public PhoneNumberNotFoundException(String message) {
			super(message);
		}
	}
	
	public static class EmailNotFoundException extends RuntimeException {
		public EmailNotFoundException(String message) {
			super(message);
		}
	}

	public static class DuplicateUserException extends RuntimeException {
		public DuplicateUserException(String message) {
			super(message);
		}
	}
	
	public static class DuplicatePhoneNumberException extends RuntimeException {
		public DuplicatePhoneNumberException(String message) {
			super(message);
		}
	}
	
	public static class DuplicateEmailException extends RuntimeException {
		public DuplicateEmailException(String message) {
			super(message);
		}
	}

	public static class InvalidEmailAddressException extends RuntimeException {
		public InvalidEmailAddressException(String message) {
			super(message);
		}
	}

	public static class InvalidPhoneNumberException extends RuntimeException {
		public InvalidPhoneNumberException(String message) {
			super(message);
		}
	}

	public static RuntimeException throwException(ExceptionType exceptionType, String message) {
		if (ExceptionType.USER_NOT_FOUND_EXCEPTION.equals(exceptionType)) {
			return new UserNotFoundException(message);
		} else if (ExceptionType.DUPLICATE_USER.equals(exceptionType)) {
			return new DuplicateUserException(message);
		} else if (ExceptionType.INVALID_EMAIL_ADDRESS.equals(exceptionType)) {
			return new InvalidEmailAddressException(message);
		} else if (ExceptionType.INVALID_PHONE_NUMBER.equals(exceptionType)) {
			return new InvalidPhoneNumberException(message);
		} else if (ExceptionType.DUPLICATE_PHONE_NUMBER.equals(exceptionType)) {
			return new DuplicatePhoneNumberException(message);
		} else if (ExceptionType.DUPLICATE_EMAIL_ADDRESS.equals(exceptionType)) {
			return new DuplicateEmailException(message);
		} else if (ExceptionType.EMAIL_NOT_FOUND_EXCEPTION.equals(exceptionType)) {
			return new EmailNotFoundException(message);
		} else if (ExceptionType.PHONE_NUMBER_NOT_FOUND_EXCEPTION.equals(exceptionType)) {
			return new PhoneNumberNotFoundException(message);
		}
		return new RuntimeException(message);
	}

}

