package com.sample.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sample.model.Email;
import com.sample.model.PhoneNumber;
import com.sample.model.User;

/**
 * This test class contains all test scenarios of UserRepository. 
 *
 * @author Mahnaz
 * @Mar 13, 2020
 */

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByName_whenGiveValidName_thenReturnUserInfo() {

        entityManager.persist(createMockUser());

        Optional<User> foundUser = userRepository.findByName("mahnaz");
        Assertions.assertNotNull(foundUser.get());
    }
    
    @Test
    public void findByName_whenGiveInvalidName_thenReturnull() {

        entityManager.persist(createMockUser());

        Optional<User> foundUser = userRepository.findByName("mah");
        Assertions.assertFalse(foundUser.isPresent());
    }

    @Test
    public void existByFullName_whenGiveExistanceName_thenReturnTrue() {

        entityManager.persist(createMockUser());

        boolean isExists = userRepository.existByFullName("mahnaz", "ebr");
        Assertions.assertEquals(true, isExists);
    }

    private User createMockUser() {
    	User user = new User("ebr", "mahnaz");
    	Set<Email> emails = new HashSet<>();
		emails.add(new Email("mah.ebr@gmail.com", user));
		Set<PhoneNumber> phoneNumbers = new HashSet<>();
		phoneNumbers.add(new PhoneNumber("989123456", user));
		user.setEmails(emails);
		user.setPhoneNumbers(phoneNumbers);
		return user;
    }
}
