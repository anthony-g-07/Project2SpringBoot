package edu.csumb.project_2.service;

// Correct imports
import edu.csumb.project_2.model.User;
import edu.csumb.project_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

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
}

