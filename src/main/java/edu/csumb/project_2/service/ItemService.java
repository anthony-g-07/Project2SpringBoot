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

    public boolean deleteItem(String itemNameOrId) {
        Item item = (Item) itemRepository.findByName(itemNameOrId);
        if (item != null) {
            itemRepository.delete(item);
            return true;
        } else {
            return false;
        }

    }


}
