package com.sample.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sample.dto.EmailDto;
import com.sample.dto.PhoneNumberDto;
import com.sample.dto.UserDto;
import com.sample.model.Email;
import com.sample.model.PhoneNumber;
import com.sample.model.User;

/**
 * This test class contains all test scenarios of UserMapperModel. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@SpringBootTest
public class UserModelMapperTest {

	@Autowired
	private UserModelMapper modelMapper;

	@Test
	public void convertUserEntityToDto_whenSendUserEntity_thenReturnUserDto() throws Exception {
		UserDto dto = modelMapper.convertUserEntityToDto(createMockUser().get());
		Assertions.assertTrue(dto.getFirstName().equals("mahnaz"));
		Assertions.assertTrue(dto.getEmails().size() == 1);
		Assertions.assertTrue(dto.getPhoneNumbers().size() == 1);
		Assertions.assertTrue(dto.getId() == 1);
	}

	@Test
	public void convertUserDtoToEntity_whenSendUserEntity_thenReturnUserDto() throws Exception {
		User entity = modelMapper.convertUserDtoToEntity(createMockUserDto());
		Assertions.assertTrue(entity.getFirstName().equals("mahnaz"));
		Assertions.assertTrue(entity.getEmails().size() == 1);
		Assertions.assertTrue(entity.getPhoneNumbers().size() == 1);
	}

	@Test
	public void convertEmailDtoToEntity_whenSendEmailDto_thenReturnEmailEntity() throws Exception {
		Email entity = modelMapper.convertEmailDtoToEntity(createMockEmailDto(), createMockUser().get());
		Assertions.assertTrue(entity.getMail().equals("m.e@gmail.com"));
		Assertions.assertTrue(entity.getUser().getFirstName().equals("mahnaz"));
	}

	@Test
	public void convertPhoneDotToEntity_whenSendPhoneDto_thenReturnPhoneEntity() throws Exception {
		PhoneNumber entity = modelMapper.convertPhoneDotToEntity(createMockPhoneDto(), createMockUser().get());
		Assertions.assertTrue(entity.getNumber().equals("989123456"));
		Assertions.assertTrue(entity.getUser().getFirstName().equals("mahnaz"));
	}

	private EmailDto createMockEmailDto() {
		EmailDto emailDto = new EmailDto(2, "m.e@gmail.com"/* , 1 */);
		return emailDto;
	}

	private PhoneNumberDto createMockPhoneDto() {
		PhoneNumberDto dto = new PhoneNumberDto(3, "989123456"/* , 1 */);
		return dto;
	}

	private Optional<User> createMockUser() {
		User user = new User("ebr", "mahnaz");

		Set<Email> emails = new HashSet<>();
		Email email = new Email("m.e@gmail.com", user);
		email.setId(2);
		emails.add(email);

		Set<PhoneNumber> phoneNumbers = new HashSet<>();
		PhoneNumber phoneNumber = new PhoneNumber("989123456", user);
		phoneNumber.setId(3);
		phoneNumbers.add(phoneNumber);

		user.setId(1);
		user.setEmails(emails);
		user.setPhoneNumbers(phoneNumbers);
		return Optional.of(user);
	}

	private UserDto createMockUserDto() {
		Set<EmailDto> emails = new HashSet<>();
		emails.add(new EmailDto(2, "m.e@gmail.com"/* , 1 */));
		Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
		phoneNumbers.add(new PhoneNumberDto(3, "989123456"/* , 1 */));

		UserDto userDto = new UserDto(1, "ebr", "mahnaz", emails, phoneNumbers);
		return userDto;
	}

}
