package edu.csumb.project_2.controller;

import edu.csumb.project_2.model.User;
import edu.csumb.project_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    // Service layer should be injected for handling business logic.
    @Autowired
    private UserService userService;

    // View all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Create a new user
    @PutMapping
    public ResponseEntity<User> createUser(@RequestBody User userDetails) {
        User createdUser = userService.saveUser(userDetails);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Delete a user
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        boolean isDeleted = userService.deleteUser(username);
        if (isDeleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // Update a user
    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestParam String username, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(username, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // New delete function with password verification
    @DeleteMapping("/secure-delete")
    public ResponseEntity<String> deleteUserWithPassword(@RequestParam String username, @RequestParam String password) {
        boolean isVerified = userService.verifyPassword(username, password);
        if (isVerified) {
            boolean isDeleted = userService.deleteUser(username);
            if (isDeleted) {
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }
    }


}
