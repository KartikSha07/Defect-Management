package com.cognizant.repository.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
 
import com.cognizant.entities.User;
import com.cognizant.main.DefectsManagementApplication;
import com.cognizant.repositries.UserRepository;
 
@DataJpaTest
@ContextConfiguration(classes = DefectsManagementApplication.class)
public class TestUserRepository {
	
    @Autowired
    private UserRepository userRepository;
 
    @Test
    void testSaveUser() {
        // Create a user object
        User user = new User();
        user.setUserName("john_doe");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
        user.setAccountLocked(false);
 
        // Save the user using the repository
        User savedUser = userRepository.save(user);
 
        // Verify that the saved user is not null
        assertNotNull(savedUser);
 
        // Verify that the saved user has an ID assigned
        assertNotNull(savedUser.getId());
    }
 
    @Test
    void testFindByUserNameAndPassword() {
        // Create a user object and save it to the database
        User user = new User();
        user.setUserName("john_doe");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
        user.setAccountLocked(false);
        userRepository.save(user);
 
        // Call the findByUserNameAndPassword method of the userRepository
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByUserNameAndPassword("john_doe", "password123"));
 
        // Verify that the returned user is not empty
        assertTrue(foundUser.isPresent());
 
        // Verify that the attributes of the returned user match the expected values
        assertEquals("john_doe", foundUser.get().getUserName());
        assertEquals("password123", foundUser.get().getPassword());
        assertEquals("ROLE_USER", foundUser.get().getRole());
        assertFalse(foundUser.get().isAccountLocked());
    }
 
    @Test
    void testFindByUserNameAndPassword_InvalidCredentials() {
        // Call the findByUserNameAndPassword method of the userRepository with invalid credentials
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByUserNameAndPassword("invalid_username", "invalid_password"));
 
        // Verify that the returned user is empty
        assertTrue(foundUser.isEmpty());
    }
 
    @Test
    void testDeleteUserById() {
        // Create a user object and save it to the database
        User user = new User();
        user.setUserName("john_doe");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
        user.setAccountLocked(false);
        userRepository.save(user);
 
        // Delete the user by ID
        userRepository.deleteById(user.getId());
 
        // Verify that the user is no longer present in the database
        assertFalse(userRepository.existsById(user.getId()));
    }
 
}