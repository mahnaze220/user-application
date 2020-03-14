package com.sample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sample.model.PhoneNumber;
import com.sample.model.User;

/**
 * This test class contains all test scenarios of PhoneNumberRepository. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@DataJpaTest
public class PhoneNumberRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    public void existByNumber_whenGiveExistanceNumber_thenReturnTrue() {
    	User user = new User("ebr", "mahnaz");
		PhoneNumber phone = new PhoneNumber("989123456", user);
		
		entityManager.persist(user);
        entityManager.persist(phone);

        boolean isExists = phoneRepository.existByNumber("989123456");
        Assertions.assertEquals(true, isExists);
    }
}
