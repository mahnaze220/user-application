package com.sample.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains user information to add new or update existing one.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@RequiredArgsConstructor
public class UserDto {

	@JsonProperty("id")
	@ApiModelProperty(notes = "id")
	private Integer id;
	
	@NotNull(message = "Last name must not be null")
	@JsonProperty("lastName")
	@ApiModelProperty(notes = "lastName", required = true)
	@Pattern(regexp = "\\D+", message = "Invalid last name!")
	@Valid
	private String lastName;
	
	@NotNull(message = "First name must not be null")
	@JsonProperty("firstName")
	@ApiModelProperty(notes = "firstName", required = true)
	@Pattern(regexp = "\\D+", message = "Invalid first name!")
	@Valid
	private String firstName;
	
	@JsonProperty("emails")
	@ApiModelProperty(notes = "emails")
	@Valid
	private Set<EmailDto> emails;
	
	@JsonProperty("phoneNumbers")
	@ApiModelProperty(notes = "phoneNumbers")
	@Valid
	private Set<PhoneNumberDto> phoneNumbers;
	
	public UserDto(@NotNull String lastName, @NotNull String firstName, 
			Set<EmailDto> emails, Set<PhoneNumberDto> phoneNumbers) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.emails = emails;
		this.phoneNumbers = phoneNumbers;
	}
	
	public UserDto(Integer id, @NotNull String lastName, @NotNull String firstName, 
			Set<EmailDto> emails, Set<PhoneNumberDto> phoneNumbers) {
		this(lastName, firstName, emails, phoneNumbers);
		this.id = id;
	}

}
