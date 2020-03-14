package com.sample.dto;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This DTO contains contact information of user for adding new one of updating existing 
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
	
	@JsonProperty("userId")
	@ApiModelProperty(notes = "userId")
	private Integer userId;
}
