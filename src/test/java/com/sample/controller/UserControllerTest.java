package com.sample.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dto.ContactInfoRequest;
import com.sample.dto.EmailDto;
import com.sample.dto.PhoneNumberDto;
import com.sample.dto.UserDto;
import com.sample.exception.UserServiceException.DuplicateUserException;
import com.sample.exception.UserServiceException.UserNotFoundException;
import com.sample.service.UserService;

/**
 * This test class contains all test scenarios of UserController. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void findUserById_whenUserIdHasValidValue_thenReturnUserInfo() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.findUserById(Mockito.any()))
		.thenReturn(userDto);

		mockMvc.perform(get("/findUserById?userId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andExpect(status().isOk());
	}

	@Test
	public void findUserName_whenUserNameHasValidValue_thenReturnUserInfo() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.findUserByName(Mockito.any()))
		.thenReturn(userDto);

		mockMvc.perform(get("/findUserByName?userName=mahnaz")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andExpect(status().isOk());
	}

	@Test
	public void deleteUser_whenUserIdHasValidValue_thenDeleteUser() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.deleteUser(Mockito.any()))
		.thenReturn(true);

		mockMvc.perform(delete("/deleteUser?userId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andExpect(status().isOk());
	}

	@Test
	public void deleteUser_whenUserNotExist_thenThrowUserNotFoundException() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.deleteUser(Mockito.any()))
		.thenThrow(new DuplicateUserException("User not found"));

		MvcResult result = mockMvc.perform(delete("/deleteUser?userId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		Exception exception = result.getResolvedException();
		Assertions.assertTrue(exception.getMessage().equals("User not found"));
	}

	@Test
	public void createUser_whenRequestIsValid_thenSendOkStatusCode() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.createUser(Mockito.any()))
		.thenReturn(userDto);

		MvcResult mvcResult = mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isCreated())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assertions.assertTrue(objectMapper.writeValueAsString(userDto)
				.equals(result));
	}

	@Test
	public void createUser_whenUserIsDuplicate_thenThrowDuplicateUserException() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.createUser(Mockito.any()))
		.thenThrow(new DuplicateUserException("User is duplicate!"));

		MvcResult result = mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
		Exception exception = result.getResolvedException();
		Assertions.assertTrue(exception.getMessage().equals("User is duplicate!"));
	}

	@Test
	void createUser_whenEmailIsInvalid_thenReturnsInvalidEmailAddress() throws Exception {
		Set<EmailDto> emails = new HashSet<>();
		emails.add(new EmailDto(2, "mah.ebr@gmail", 1));
		UserDto userDto = new UserDto(1, "ebr", "mahnaz", emails, null);

		mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.timestamp", is(notNullValue())))
		.andExpect(jsonPath("$.errors", hasItem("Invalid email address!")));
	}

	@Test
	void createUser_whenPhoneNumberIsInvalid_thenReturnsInvalidPhoneNumber() throws Exception {
		Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
		phoneNumbers.add(new PhoneNumberDto(3, "98912frt", 1));
		UserDto userDto = new UserDto(1, "ebr", "mahnaz", null, phoneNumbers);

		mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.timestamp", is(notNullValue())))
		.andExpect(jsonPath("$.errors", hasItem("Invalid phone number!")));
	}

	@Test
	void createUser_whenFirstNameIsInvalid_thenReturnsInvalidFirstName() throws Exception {
		UserDto userDto = new UserDto(1, "ebr", "mahnaz23", null, null);

		mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.timestamp", is(notNullValue())))
		.andExpect(jsonPath("$.errors", hasItem("Invalid first name!")));
	}

	@Test
	void createUser_whenLastNameIsInvalid_thenReturnsInvalidLastName() throws Exception {
		UserDto userDto = new UserDto(1, "ebr123", "mahnaz", null, null);

		mockMvc.perform(post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.timestamp", is(notNullValue())))
		.andExpect(jsonPath("$.errors", hasItem("Invalid last name!")));
	}

	@Test
	public void addContactInfo_whenRequestValidContactInfo_thenReturnUserInfo() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.addContactInfo(Mockito.any()))
		.thenReturn(userDto);

		MvcResult mvcResult = mockMvc.perform(post("/addContactInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isCreated())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assertions.assertTrue(objectMapper.writeValueAsString(userDto)
				.equals(result));
	}

	@Test
	public void addContactInfo_whenUserIdIsNotValid_thenThrowUserNotFoundException() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.addContactInfo(Mockito.any()))
		.thenThrow(new UserNotFoundException("User not found"));

		MvcResult mvcResult = mockMvc.perform(post("/addContactInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isNotFound())
				.andReturn();
		Exception exception = mvcResult.getResolvedException();
		Assertions.assertTrue(exception.getMessage().equals("User not found"));
	}

	@Test
	public void updateContactInfo_whenRequestValidContactInfo_thenReturnUserInfo() throws Exception {
		UserDto userDto = createMockUserDto();
		Mockito.when(userService.updateContactInfo(Mockito.any()))
		.thenReturn(userDto);

		MvcResult mvcResult = mockMvc.perform(put("/updateContactInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assertions.assertTrue(objectMapper.writeValueAsString(userDto)
				.equals(result));
	}

	@Test
	void updateContactInfo_whenEmailIsInvalid_thenReturnsInvalidEmailAddress() throws Exception {
		Set<EmailDto> emails = new HashSet<>();
		EmailDto email = new EmailDto(2, "mah.ebr@gmail", 1);
		emails.add(email);

		ContactInfoRequest contactInfoRequest = new ContactInfoRequest();
		contactInfoRequest.setUserId(1);
		contactInfoRequest.setEmail(email);

		mockMvc.perform(put("/updateContactInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(contactInfoRequest)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.timestamp", is(notNullValue())))
		.andExpect(jsonPath("$.errors", hasItem("Invalid email address!")));
	}

	private UserDto createMockUserDto() {
		Set<EmailDto> emails = new HashSet<>();
		emails.add(new EmailDto(2, "mah.ebr@gmail.com", 1));
		Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
		phoneNumbers.add(new PhoneNumberDto(3, "989123456", 1));

		UserDto userDto = new UserDto(1, "ebr", "mahnaz", emails, phoneNumbers);
		return userDto;
	}
}
