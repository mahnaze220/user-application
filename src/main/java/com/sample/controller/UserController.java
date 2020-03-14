package com.sample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.dto.ContactInfoRequest;
import com.sample.dto.UserDto;
import com.sample.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This controller provides REST services to create, find, delete a user and
 * also add and update user's contact information.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@RestController
@Validated
@Api(value = "API to create, update, delete and search a user", produces = "application/json")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Creates a new user with the contact info
	 * 
	 * @param createUserRequest - {@link UserDto} request of creating a new user
	 * @return UserDto - information of the created user
	 */
	@PostMapping(value = "/createUser")
	@ApiOperation(value = "Create new user with contact info", response = UserDto.class)
	@ApiParam(name = "createUserRequest", value = "New user information to be created", required = true)
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody final UserDto createUserRequest) {
		return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
	}

	/**
	 * Finds the user by the id
	 * 
	 * @param userId - id of the user
	 * @return UserDto - information of the found user
	 */
	@GetMapping(value = "/findUserById")
	@ApiOperation(value = "Find user by ID", response = UserDto.class)
	@ApiParam(name = "userId", value = "The ID of the user to be searched", required = true)
	public ResponseEntity<UserDto> findUserById(@RequestParam final Integer userId) {
		return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
	}

	/**
	 * Finds the user by the name
	 * 
	 * @param userName - first name or last name of the user
	 * @return UserDto - information of the found user
	 */
	@GetMapping(value = "/findUserByName")
	@ApiOperation(value = "Find user by name", response = UserDto.class)
	@ApiParam(name = "userId", value = "The first name or last name of the user to be searched", required = true)
	public ResponseEntity<UserDto> findUserByName(@RequestParam final String userName) {
		return new ResponseEntity<>(userService.findUserByName(userName), HttpStatus.OK);
	}

	/**
	 * Deletes the user by the id
	 * 
	 * @param userId - id of the user
	 * @return Long - response status code
	 */
	@DeleteMapping(value = "/deleteUser")
	@ApiOperation(value = "Delete user by ID")
	@ApiParam(name = "userId", value = "The ID of the user to be deleted", required = true)
	public ResponseEntity<Long> deleteUser(@RequestParam final Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Adds new phone number and email address to the user's contact info
	 * 
	 * @param contactInfoRequest - {@link ContactInfoRequest} request of new contact info
	 * @return UserDto - information of updated user
	 */
	@PostMapping(value = "/addContactInfo")
	@ApiOperation(value = "Add contact info (email/phone number) for the user", response = UserDto.class)
	@ApiParam(name = "contactInfoRequest", value = "New contact information to be created", required = true)
	public ResponseEntity<UserDto> addContactInfo(@Valid @RequestBody final ContactInfoRequest contactInfoRequest) {
		return new ResponseEntity<>(userService.addContactInfo(contactInfoRequest), HttpStatus.CREATED);
	}

	/**
	 * Updates existing phone number or email address of the user
	 * 
	 * @param contactInfoRequest - {@link ContactInfoRequest} request of updating contact info
	 * @return UserDto information of updated user
	 */
	@PutMapping(value = "/updateContactInfo")
	@ApiOperation(value = "Update contact info (email/phone number) of the user", response = UserDto.class)
	@ApiParam(name = "contactInfoRequest", value = "Contact information to be updated", required = true)
	public ResponseEntity<UserDto> updateContactInfo(@Valid @RequestBody final ContactInfoRequest contactInfoRequest) {
		return new ResponseEntity<>(userService.updateContactInfo(contactInfoRequest), HttpStatus.OK);
	}
}
