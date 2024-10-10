package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByUserIdAndListId(String userId, String listId);
    List<Item> findByListId(String listId);

    List<Item> findByName(String name);

    List<Item> findByUserId(String userId);

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Item> findByNameRegex(String regex);

}
