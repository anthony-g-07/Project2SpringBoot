package edu.csumb.project_2.controller;

import edu.csumb.project_2.model.User;
import edu.csumb.project_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during signup: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User authenticatedUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

 @GetMapping("/fix-duplicates")
    public ResponseEntity<?> fixDuplicateUsers(@RequestParam String username) {
        List<User> duplicates = userService.findDuplicateUsers(username);

        if (duplicates.size() > 1) {
            // Keep the first entry and remove the rest
            for (int i = 1; i < duplicates.size(); i++) {
                userService.deleteUserById(duplicates.get(i).getId());
            }
            return ResponseEntity.ok("Duplicates removed for username: " + username);
        } else {
            return ResponseEntity.ok("No duplicates found for username: " + username);
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");

        boolean isPasswordChanged = userService.changePassword(username, currentPassword, newPassword);

        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(400).body("Current password is incorrect or user not found.");
        }
    }

@PostMapping("/change-username")
    public ResponseEntity<?> changeUsername(@RequestBody Map<String, String> request) {
        String currentUsername = request.get("currentUsername");
        String newUsername = request.get("newUsername");
        String password = request.get("password");

        User updatedUser = userService.changeUsername(currentUsername, newUsername, password);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.badRequest().body("Username change failed.");
        }
    }

}


