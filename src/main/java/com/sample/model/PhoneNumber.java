package com.sample.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * This entity holds persisted phone number information of a user. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@EqualsAndHashCode(exclude="user")
@RequiredArgsConstructor
@Entity
@Table(name = "PHONE_NUMBER")
public class PhoneNumber {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	private String number;

	/*
	 * Each phone number is unique and has only one owner
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name="user", referencedColumnName = "id", nullable = false)
	private User user;
	
	public PhoneNumber(@NotNull String number, User user) {
		super();
		this.user = user;
		this.number = number;
	}
}
