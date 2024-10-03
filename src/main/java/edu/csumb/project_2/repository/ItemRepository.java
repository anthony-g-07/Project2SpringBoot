package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByUserIdAndListId(String userId, String listId);
    List<Item> findByListId(String listId);
    Optional<Item> findById(String itemId);

    List<Item> findByName(String name);

}
