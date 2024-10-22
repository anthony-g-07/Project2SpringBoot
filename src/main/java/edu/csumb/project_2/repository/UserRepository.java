package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    List<User> findAllByUsername(String username); // Handles finding all duplicate users

    Optional<User> findOptionalByUsername(String username);
}
