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

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Add a new item
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // Add item to a collection (if needed)
   public Item addItemToCollection(Item item) {
    // Ensure the category is properly set and only added to the intended collection
    return itemRepository.save(item);
}

    // Save (update) an existing item
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

     // Search items by name or description using regex
    public List<Item> searchItems(String searchTerm) {
        return itemRepository.findByNameOrDescriptionRegex(searchTerm);
    }


    // Fetch all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Fetch item by ID
    public Item getItemById(String itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    // Delete an item by ID
    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }

    // Fetch items by category
    public List<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }



}
