package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    // Find items by name
    List<Item> findByName(String name);
    
    List<Item> findByNameInOrDescriptionIn(List<String> names, List<String> descriptions);


    // Find items using a regular expression
 @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<Item> findByNameOrDescriptionRegex(String regex);


    // Find items by category
    List<Item> findByCategory(String category);
}