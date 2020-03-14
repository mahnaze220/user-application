package com.sample.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dto.ContactInfoRequest;
import com.sample.dto.EmailDto;
import com.sample.dto.PhoneNumberDto;
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
 * This service provides methods for adding and updating user infromation. 
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

	public UserDto createUser(UserDto createUserRequest) {
		boolean isUserExist = userRepository.existByFullName(createUserRequest.getFirstName(), 
				createUserRequest.getLastName());
		if(isUserExist) {
			throw UserServiceException.throwException(ExceptionType.DUPLICATE_USER, "User is duplicate!");
		}
		else {
			User createdUser = userRepository.save(convertUserDtoToEntity(createUserRequest));
			return convertUserEntityToDto(createdUser);
		}
	}

	public UserDto findUserById(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return convertUserEntityToDto(user.get());
		}
		else {
			log.info("User not found");
			return null;
		}
	}

	public UserDto findUserByName(String userName) {
		Optional<User> user = userRepository.findByName(userName);
		if(user.isPresent()) {
			return convertUserEntityToDto(user.get());
		}
		else {
			log.info("User not found");
			return null;
		}
	}

	public Boolean deleteUser(Integer userId) {
		boolean isExists = userRepository.existsById(userId);
		if(isExists) {
			userRepository.deleteById(userId);
			return true;
		}
		else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
	}

	public UserDto addContactInfo(ContactInfoRequest contactInfoRequest) {
		Optional<User> user = userRepository.findById(contactInfoRequest.getUserId());
		if(user.isPresent()) {
			if(contactInfoRequest.getPhoneNumber() != null) {
				if(!phoneRepository.existByNumber(contactInfoRequest.getPhoneNumber().getNumber())) {
					phoneRepository.save(convertPhoneDotToEntity(contactInfoRequest.getPhoneNumber(), user.get()));					
				}
				else {
					throw UserServiceException.throwException(ExceptionType.DUPLICATE_PHONE_NUMBER, 
							"Duplicate phone number!");
				}
			}

			if(contactInfoRequest.getEmail() != null) {
				if(!emailRepository.existByMail(contactInfoRequest.getEmail().getMail())) {
					emailRepository.save(convertEmailDtoToEntity(contactInfoRequest.getEmail(), user.get()));
				}
				else {
					throw UserServiceException.throwException(ExceptionType.DUPLICATE_EMAIL_ADDRESS, 
							"Duplicate email address!");
				}
			}
		}
		else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
		return convertUserEntityToDto(user.get());
	}

	public UserDto updateContactInfo(ContactInfoRequest contactInfoRequest) {
		Optional<User> user = userRepository.findById(contactInfoRequest.getUserId());
		if(user.isPresent()) {
			if(contactInfoRequest.getPhoneNumber() != null) {
				Optional<PhoneNumber> foundNumber = 
						phoneRepository.findById(contactInfoRequest.getPhoneNumber().getId());
				if(foundNumber.isPresent()) {
					foundNumber.get().setNumber(contactInfoRequest.getPhoneNumber().getNumber());
					phoneRepository.save(foundNumber.get());
				}
				else {
					throw UserServiceException.throwException(ExceptionType.PHONE_NUMBER_NOT_FOUND_EXCEPTION, 
							"Phone number not found");
				}		
			}

			if(contactInfoRequest.getEmail() != null) {
				Optional<Email> foundEmail = 
						emailRepository.findById(contactInfoRequest.getEmail().getId());
				if(foundEmail.isPresent()) {
					foundEmail.get().setMail(contactInfoRequest.getEmail().getMail());
					emailRepository.save(foundEmail.get());
				}
				else {
					throw UserServiceException.throwException(ExceptionType.EMAIL_NOT_FOUND_EXCEPTION, 
							"Email address not found");
				}
			}
		}
		else {
			throw UserServiceException.throwException(ExceptionType.USER_NOT_FOUND_EXCEPTION, "User not found");
		}
		return convertUserEntityToDto(user.get());
	}

	public Email convertEmailDtoToEntity(EmailDto emailDto, User user) {
		ModelMapper modelMapper = new ModelMapper();
		Email email = modelMapper.map(emailDto, Email.class);
		email.setUser(user);
		return email;
	}

	public PhoneNumber convertPhoneDotToEntity(PhoneNumberDto phoneDto, User user) {
		ModelMapper modelMapper = new ModelMapper();
		PhoneNumber phone = modelMapper.map(phoneDto, PhoneNumber.class);
		phone.setUser(user);
		return phone;
	}

	public UserDto convertUserEntityToDto(User userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		return  modelMapper.map(userEntity, UserDto.class);
	}

	public User convertUserDtoToEntity(UserDto userDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDto, User.class);
		user.getEmails().forEach(email -> email.setUser(user));
		user.getPhoneNumbers().forEach(phone -> phone.setUser(user));
		return user;
	}
}
