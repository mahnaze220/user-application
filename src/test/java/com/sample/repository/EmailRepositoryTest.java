package com.sample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sample.model.Email;
import com.sample.model.User;

/**
 * This test class contains all test scenarios of EmailRepository. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@DataJpaTest
public class EmailRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmailRepository emailRepository;

    @Test
    public void existByMail_whenGiveExistanceEmail_thenReturnTrue() {

    	User user = new User("ebr", "mahnaz");
		Email email = new Email("mah.ebr@gmail.com", user);
		
		entityManager.persist(user);
        entityManager.persist(email);

        boolean isExists = emailRepository.existByMail("mah.ebr@gmail.com");
        Assertions.assertEquals(true, isExists);
    }
}
