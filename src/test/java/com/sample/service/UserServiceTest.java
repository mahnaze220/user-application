package com.sample.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sample.dto.ContactInfoRequest;
import com.sample.dto.EmailDto;
import com.sample.dto.PhoneNumberDto;
import com.sample.dto.UserDto;
import com.sample.exception.UserServiceException.DuplicateEmailException;
import com.sample.exception.UserServiceException.DuplicatePhoneNumberException;
import com.sample.exception.UserServiceException.DuplicateUserException;
import com.sample.exception.UserServiceException.EmailNotFoundException;
import com.sample.exception.UserServiceException.UserNotFoundException;
import com.sample.model.Email;
import com.sample.model.PhoneNumber;
import com.sample.model.User;
import com.sample.repository.EmailRepository;
import com.sample.repository.PhoneRepository;
import com.sample.repository.UserRepository;

/**
 * This test class contains all test scenarios of UserService. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PhoneRepository phoneRepository;

	@MockBean
	private EmailRepository emailRepository;

	@Test
	public void findUserById_whenUserIdHasValidValue_thenReturnUserInfo() throws Exception {
		Optional<User> user = createMockUser();
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(user);

		UserDto userDto = userService.findUserById(1);
		Assertions.assertTrue(userDto.getFirstName().equals("mahnaz"));
	}

	@Test
	public void findUserById_whenUserIdIsNotValid_thenReturnNull() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(Optional.ofNullable(null));

		UserDto userDto = userService.findUserById(2);
		Assertions.assertNull(userDto);
	}

	@Test
	public void findUserByName_whenUserNameHasValidValue_thenReturnUserInfo() throws Exception {
		Optional<User> user = createMockUser();
		Mockito.when(userRepository.findByName(Mockito.any()))
		.thenReturn(user);

		UserDto userDto = userService.findUserByName("mahnaz");
		Assertions.assertTrue(userDto.getId().equals(1));
	}

	@Test
	public void findUserByName_whenUserNameIsNotValid_thenReturnNull() throws Exception {
		Mockito.when(userRepository.findByName(Mockito.any()))
		.thenReturn(Optional.ofNullable(null));

		UserDto userDto = userService.findUserByName("mahnaz");
		Assertions.assertNull(userDto);
	}

	@Test
	public void createUser_whenSendDuplicateUser_thenThrowDuplicateUserException() throws Exception {
		Mockito.when(userRepository.existByFullName(Mockito.any(), Mockito.any()))
		.thenReturn(true);
		UserDto userDto = createMockUserDto();

		Assertions.assertThrows(DuplicateUserException.class, () -> {
			userService.createUser(userDto);
		}, "User is duplicate!");
	}

	@Test
	public void createUser_whenSendValidUser_thenSaveUser() throws Exception {
		Mockito.when(userRepository.existByFullName(Mockito.any(), Mockito.any()))
		.thenReturn(false);
		UserDto userDto = createMockUserDto();
		Optional<User> user = createMockUser();
		Mockito.when(userRepository.save(Mockito.any()))
		.thenReturn(user.get());
		UserDto result = userService.createUser(userDto);
		Assertions.assertTrue(result.getFirstName().equals("mahnaz"));
	}

	@Test
	public void deleteUser_whenUserDoesNotExist_thenThrowUserNotFoundException() throws Exception {
		Mockito.when(userRepository.existsById(Mockito.any()))
		.thenReturn(false);
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			userService.deleteUser(1);
		}, "User not found");
	}

	@Test
	public void addContactInfo_whenSendValidContactInfo_thenSaveContactInfo() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(createMockUser());
		Mockito.when(phoneRepository.existByNumber(Mockito.any()))
		.thenReturn(false);

		UserDto result = userService.addContactInfo(createMockPhoneNumber());
		Assertions.assertTrue(result.getPhoneNumbers().size() == 1);
	}

	@Test
	public void addContactInfo_whenSendDuplicatePhoneNumber_thenThrowDuplicatePhoneNumberException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(createMockUser());
		Mockito.when(phoneRepository.existByNumber(Mockito.any()))
		.thenReturn(true);
		Assertions.assertThrows(DuplicatePhoneNumberException.class, () -> {
			userService.addContactInfo(createMockPhoneNumber());
		}, "Duplicate phone number!");
	}

	@Test
	public void addContactInfo_whenSendDuplicateEmail_thenThrowDuplicateEmailException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(createMockUser());
		Mockito.when(emailRepository.existByMail(Mockito.any()))
		.thenReturn(true);
		Assertions.assertThrows(DuplicateEmailException.class, () -> {
			userService.addContactInfo(createMockEmail());
		}, "Duplicate email address!");
	}

	@Test
	public void addContactInfo_whenUserIsNotValid_thenThrowUserNotFoundException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			userService.addContactInfo(createMockPhoneNumber());
		}, "User not found");
	}

	@Test
	public void updateContactInfo_whenUserIsNotValid_thenThrowUserNotFoundException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			userService.updateContactInfo(createMockPhoneNumber());
		}, "User not found");
	}

	@Test
	public void updateContactInfo_whenEmailNotFound_thenThrowEmailNotFoundException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
		.thenReturn(createMockUser());
		Mockito.when(emailRepository.findById(Mockito.any()))
		.thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(EmailNotFoundException.class, () -> {
			userService.updateContactInfo(createMockEmail());
		}, "Email address not found");
	}

	@Test
	public void updateContactInfo_whenSendValidContactInfo_thenSaveContactInfo() throws Exception {
		User user = createMockUser().get();
		Email newEmail = new Email("m_ebr@gmail.com", user);
		newEmail.setId(2);
		Email foundEmail = new Email("m.e@gmail.com", user);
		newEmail.setId(2);
		Mockito.when(userRepository.findById(1))
		.thenReturn(createMockUser());
		Mockito.when(emailRepository.findById(2))
		.thenReturn(Optional.of(foundEmail));
		Mockito.when(emailRepository.save(newEmail))
		.thenReturn(newEmail);

		UserDto result = userService.updateContactInfo(createMockEmail());
		Assertions.assertTrue(result.getEmails().size() == 1);
	}

	private Optional<User> createMockUser() {
		User user = new User("ebr", "mahnaz");

		Set<Email> emails = new HashSet<>();
		Email email = new Email("mah.ebr@gmail.com", user);
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
		emails.add(new EmailDto(2, "mah.ebr@gmail.com", 1));
		Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
		phoneNumbers.add(new PhoneNumberDto(3, "989123456", 1));

		UserDto userDto = new UserDto(1, "ebr", "mahnaz", emails, phoneNumbers);
		return userDto;
	}

	private ContactInfoRequest createMockPhoneNumber() {
		ContactInfoRequest contactInfo = new ContactInfoRequest();
		contactInfo.setUserId(1);
		contactInfo.setPhoneNumber(new PhoneNumberDto("12345678"));
		return contactInfo;
	}

	private ContactInfoRequest createMockEmail() {
		ContactInfoRequest contactInfo = new ContactInfoRequest();
		contactInfo.setUserId(1);
		contactInfo.setEmail(new EmailDto(2, "m.e@gmail.com", 1));
		return contactInfo;
	}
}
