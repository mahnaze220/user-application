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
 * This entity holds persisted email address information of each user. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@EqualsAndHashCode(exclude="user")
@RequiredArgsConstructor
@Entity
@Table(name = "EMAIL")
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	private String mail;
	
	/*
	 * Each email address is unique and has only one owner
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name="user", referencedColumnName = "id", nullable = false)
	private User user;

	public Email(@NotNull String mail, User user) {
		super();
		this.user = user;
		this.mail = mail;
	}
}
