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

    public Optional<Item> getItemById(String itemId) {
        return itemRepository.findById(itemId);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

   public void deleteItem(String id) {
    itemRepository.deleteById(id);
}


}
