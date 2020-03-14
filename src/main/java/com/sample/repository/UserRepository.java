package com.sample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.model.User;

/**
 * This repository handles user entity's queries. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.firstName = :userName  or u.lastName = :userName")
    Optional<User> findByName(String userName);
    
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.firstName = :firstName  or u.lastName = :lastName")
    boolean existByFullName(String firstName, String lastName);
    
}
