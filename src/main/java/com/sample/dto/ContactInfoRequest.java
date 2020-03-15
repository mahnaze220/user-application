package com.sample.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This DTO contains contact information of user to add new or update existing one.
 * phone number of email address.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
public class ContactInfoRequest {

	@JsonProperty("email")
	@ApiModelProperty(notes = "email")
	@Valid
	private EmailDto email;
	
	@JsonProperty("phoneNumber")
	@ApiModelProperty(notes = "phoneNumber")
	@Valid
	private PhoneNumberDto phoneNumber;
	
	@NotNull(message = "User id must not be null")
	@JsonProperty("userId")
	@ApiModelProperty(notes = "userID", required = true)
	private Integer userId;
}
