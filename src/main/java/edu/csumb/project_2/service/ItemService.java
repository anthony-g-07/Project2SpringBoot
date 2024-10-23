package edu.csumb.project_2.service;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;


public List<Item> getItemsByCategory(String category) {
    return itemRepository.findByCategory(category);
}




    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }



    public Item getItemById(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public boolean deleteItem(String itemId) {
        // Try to find the item by ID
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            // Delete the item if it exists
            itemRepository.delete(itemOptional.get());
            return true;  // Return true indicating successful deletion
        } else {
            // Item not found
            return false;  // Return false indicating item was not found
        }
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }



    public Item addItemToCollection(Item item) {
        // Save the item to the "items" collection and return the saved item
        return itemRepository.save(item);
    }

    public List<Item> searchItems(List<String> searchTerms) {
        // Create a regex pattern that allows matching any of the terms
        String regex = searchTerms.stream()
                .map(term -> ".*" + term + ".*")  // Add '.*' to make it a contains search
                .collect(Collectors.joining("|"));  // Join with '|' to match any term

        return itemRepository.findByNameRegex(regex);
    }




}
