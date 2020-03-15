package com.sample.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dto.ContactInfoRequest;
import com.sample.dto.UserDto;
import com.sample.exception.ExceptionType;
import com.sample.exception.UserServiceException;
import com.sample.model.Email;
import com.sample.model.PhoneNumber;
import com.sample.model.User;
import com.sample.repository.EmailRepository;
import com.sample.repository.PhoneRepository;
import com.sample.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This service provides methods for adding and updating user information.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private UserModelMapper userModelMapper;

	public UserDto createUser(UserDto createUserRequest) {

		// check user existence in the database by the first and last name
		boolean isUserExist = userRepository.existByFullName(createUserRequest.getFirstName(),
				createUserRequest.getLastName());

		if (isUserExist) {
			throw UserServiceException.throwException(ExceptionType.DUPLICATE_USER, "User already exists!");
		} else {
			User createdUser = userRepository.save(userModelMapper.convertUserDtoToEntity(createUserRequest));
			log.debug("New user created sucessfully");

			// convert created user entity to DTO
			return userModelMapper.convertUserEntityToDto(createdUser);
		}
	}

	public UserDto findUserById(Integer userId) {
		log.debug("Finding user by id {}", userId);
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return userModelMapper.convertUserEntityToDto(user.get());
		} else {
			log.info("User by id {} not found", userId);
			return null;
		}
	}

	public UserDto findUserByName(String userName) {
		log.debug("Finding user by name {}", userName);
		Optional<User> user = userRepository.findByName(userName);
		if (user.isPresent()) {
			return userModelMapper.convertUserEntityToDto(user.get());
		} else {
			log.info("User by name {} not found", userName);
			return null;
		}
	}

	public Boolean deleteUser(Integer userId) {
		log.debug("Deleting user by id {}", userId);

		// check user existence by the id
		boolean isExists = userRepository.existsById(userId);
		if (isExists) {
			userRepository.deleteById(userId);
			log.info("User by id {} deleted", userId);
			return true;
		} else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
	}

	public UserDto addContactInfo(ContactInfoRequest contactInfoRequest) {
		log.debug("Adding contact info for user by id {}", contactInfoRequest.getUserId());

		// check user existence by the id
		Optional<User> user = userRepository.findById(contactInfoRequest.getUserId());
		if (user.isPresent()) {
			if (contactInfoRequest.getPhoneNumber() != null) {

				// check phone number existence by the number
				if (!phoneRepository.existByNumber(contactInfoRequest.getPhoneNumber().getNumber())) {
					phoneRepository.save(
							userModelMapper.convertPhoneDotToEntity(contactInfoRequest.getPhoneNumber(), user.get()));
					log.info("Phone number {} saved for user by id {}", contactInfoRequest.getPhoneNumber().getNumber(),
							user.get().getId());
				} else {
					throw UserServiceException.throwException(ExceptionType.DUPLICATE_PHONE_NUMBER,
							"Duplicate phone number!");
				}
			}

			if (contactInfoRequest.getEmail() != null) {

				// check phone number existence by the address
				if (!emailRepository.existByMail(contactInfoRequest.getEmail().getMail())) {
					emailRepository
							.save(userModelMapper.convertEmailDtoToEntity(contactInfoRequest.getEmail(), user.get()));
					log.info("Email address {} saved for user by id {}", contactInfoRequest.getEmail().getMail(),
							user.get().getId());
				} else {
					throw UserServiceException.throwException(ExceptionType.DUPLICATE_EMAIL_ADDRESS,
							"Duplicate email address!");
				}
			}
		} else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
		return userModelMapper.convertUserEntityToDto(user.get());
	}

	public UserDto updateContactInfo(ContactInfoRequest contactInfoRequest) {
		log.debug("Udating contact info for user by id {}", contactInfoRequest.getUserId());

		// check user existence by the id
		Optional<User> user = userRepository.findById(contactInfoRequest.getUserId());
		if (user.isPresent()) {
			if (contactInfoRequest.getPhoneNumber() != null) {

				// check phone number existence by the id
				Optional<PhoneNumber> foundNumber = phoneRepository
						.findById(contactInfoRequest.getPhoneNumber().getId());
				if (foundNumber.isPresent()) {
					foundNumber.get().setNumber(contactInfoRequest.getPhoneNumber().getNumber());
					phoneRepository.save(foundNumber.get());
					log.info("Phone number {} updated for user by id {}", foundNumber.get().getNumber(),
							user.get().getId());

				} else {
					throw UserServiceException.throwException(ExceptionType.PHONE_NUMBER_NOT_FOUND_EXCEPTION,
							"Phone number not found");
				}
			}

			if (contactInfoRequest.getEmail() != null) {

				// check email address existence by the id
				Optional<Email> foundEmail = emailRepository.findById(contactInfoRequest.getEmail().getId());
				if (foundEmail.isPresent()) {
					foundEmail.get().setMail(contactInfoRequest.getEmail().getMail());
					emailRepository.save(foundEmail.get());
					log.info("Email address {} updated for user by id {}", foundEmail.get().getMail(),
							user.get().getId());
				} else {
					throw UserServiceException.throwException(ExceptionType.EMAIL_NOT_FOUND_EXCEPTION,
							"Email address not found");
				}
			}
		} else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
		return userModelMapper.convertUserEntityToDto(user.get());
	}
}
