package com.sample.dto;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains phone number information to add new one or update existing number.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@RequiredArgsConstructor
public class PhoneNumberDto {

	@JsonProperty("id")
	@ApiModelProperty(notes = "id")
	private Integer id;
	
	@JsonProperty("number")
	@ApiModelProperty(notes = "number", required = true)
	@Pattern(regexp = "\\d+", message = "Invalid phone number!")
	private String number;
	
	public PhoneNumberDto(String number) {
		super();
		this.number = number;
	}
	
	public PhoneNumberDto(Integer id, String number) {
		this(number);
		this.id = id;
	}
}
