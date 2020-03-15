package com.sample.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.sample.dto.EmailDto;
import com.sample.dto.PhoneNumberDto;
import com.sample.dto.UserDto;
import com.sample.model.Email;
import com.sample.model.PhoneNumber;
import com.sample.model.User;

/**
 * This class convert entities to DTOs and vice versa by using ModelMapper library.  
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Component
public class UserModelMapper {

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
		if(user.getEmails() != null) {
			user.getEmails()
			.stream()
			.filter(email -> email != null && email.getMail() != null)
			.forEach(email -> email.setUser(user));
		}
		if(user.getPhoneNumbers() != null) {
			user.getPhoneNumbers()
			.stream()
			.filter(phone -> phone != null && phone.getNumber() != null)
			.forEach(phone -> phone.setUser(user));
		}
		return user;
	}
}
