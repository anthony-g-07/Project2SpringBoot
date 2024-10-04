package edu.csumb.project_2.service;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItemByUser(String userId, String listId) {
        return itemRepository.findByUserIdAndListId(userId, listId);
    }

    public List<Item> getItemsByListId(String listId) {
        return itemRepository.findByListId(listId);
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

    public List<Item> getItemsByUserId(String userId) {
        return itemRepository.findByUserId(userId);
    }

    public Item addItemToCollection(Item item) {
        // Save the item to the "items" collection and return the saved item
        return itemRepository.save(item);
    }




}
