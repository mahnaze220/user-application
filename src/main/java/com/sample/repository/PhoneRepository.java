package com.sample.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sample.model.PhoneNumber;

/**
 * This repository handles phone number entity's queries. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

public interface PhoneRepository extends CrudRepository<PhoneNumber, Integer> {

	@Query("SELECT COUNT(p) > 0 FROM PhoneNumber p WHERE p.number = :phoneNumber")
    boolean existByNumber(String phoneNumber);
}
