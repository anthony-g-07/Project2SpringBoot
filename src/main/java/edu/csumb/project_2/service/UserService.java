package edu.csumb.project_2.service;

// Correct imports
import edu.csumb.project_2.model.User;
import edu.csumb.project_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Login success
        }
        return null; // Login failure
    }

    public List<User> findDuplicateUsers(String username) {
        return userRepository.findAllByUsername(username);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    // Change password method
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Change username method
    public User changeUsername(String currentUsername, String newUsername, String password) {
        User user = userRepository.findByUsername(currentUsername);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            user.setUsername(newUsername);
            return userRepository.save(user);
        }
        return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(String username) {
        // Find the user by username
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);

        // If the user is found, delete and return true
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }

        // If not found, return false
        return false;
    }

    public User updateUser(String username, User userDetails) {
        // Find the existing user by username
        User existingUser = userRepository.findByUsername(username);

        // Check if the user exists
        if (existingUser != null) {
            // Update user fields with the new details, as appropriate
            if (userDetails.getName() != null) {
                existingUser.setName(userDetails.getName());
            }
            if (userDetails.getUsername() != null) {
                existingUser.setUsername(userDetails.getUsername());
            }
            if (userDetails.getPassword() != null) {
                // Encode the new password before saving it
                String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
                existingUser.setPassword(encodedPassword);
            }
            if (userDetails.getIsAdmin() != null) {
                existingUser.setIsAdmin(userDetails.getIsAdmin());
            }

            // Save the updated user back to the repository
            return userRepository.save(existingUser);
        }

        // Return null or throw an exception if the user is not found
        throw new RuntimeException("User not found");
    }
}

