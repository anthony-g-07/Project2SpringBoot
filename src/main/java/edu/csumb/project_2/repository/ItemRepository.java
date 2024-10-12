package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findByName(String name);



    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Item> findByNameRegex(String regex);

}
