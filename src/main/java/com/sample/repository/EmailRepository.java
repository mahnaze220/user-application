package com.sample.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.model.Email;

/**
 * This repository handles email entity's queries. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {
	
	@Query("SELECT COUNT(e) > 0 FROM Email e WHERE e.mail = :mail")
    boolean existByMail(String mail);
}
