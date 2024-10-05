package edu.csumb.project_2.repository;

import edu.csumb.project_2.model.ItemList;
import edu.csumb.project_2.repository.custom.ItemListRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemListRepository extends MongoRepository<ItemList, String>, ItemListRepositoryCustom {
    // Find all lists for a specific user
    List<ItemList> findByUserId(String userId);

    // Find a list by its name
    List<ItemList> findByName(String name);

    // Optional: Find public lists
    List<ItemList> findByisPublic(boolean isPublic);
}
