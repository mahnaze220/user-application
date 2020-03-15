package com.sample.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This entity holds persisted user information. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String firstName;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Email> emails;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<PhoneNumber> phoneNumbers;
	
	public User(@NotNull String lastName, @NotNull String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}	
}
