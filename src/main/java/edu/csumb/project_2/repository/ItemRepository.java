package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    // Find items by name
    List<Item> findByName(String name);

    // Find items using a regular expression
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Item> findByNameRegex(String regex);

    // Find items by category
    List<Item> findByCategory(String category);
}