package com.sample.dto;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains email address information to add new one or update existing address.
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@RequiredArgsConstructor
public class EmailDto {
	
	@JsonProperty("id")
	@ApiModelProperty(notes = "id")
	private Integer id;
	
	@JsonProperty("mail")
	@ApiModelProperty(notes = "mail", required = true)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
			message = "Invalid email address!")
	private String mail;
	
	public EmailDto(Integer id, String mail) {
		this(mail);
		this.id = id;
	}
	
	public EmailDto(String mail) {
		super();
		this.mail = mail;
	}
}
