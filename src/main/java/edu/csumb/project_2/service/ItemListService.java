package edu.csumb.project_2.service;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.model.ItemList;
import edu.csumb.project_2.repository.ItemListRepository;
import edu.csumb.project_2.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemListService {

    private final ItemListRepository itemListRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ItemListService(ItemListRepository itemListRepository) {
        this.itemListRepository = itemListRepository;
    }

    public List<ItemList> findListsByUserId(String userId) {
        return itemListRepository.findByUserId(userId);
    }

    public ItemList saveItemList(ItemList itemList) {
        return itemListRepository.save(itemList);
    }

    public void deleteItemList(String listId) {
        itemListRepository.deleteById(listId);
    }

    public ItemList getListById(String listId) {
        return itemListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found with id: " + listId));
    }

    public ItemList updateList(String listId, ItemList updatedList) {
        ItemList existingList = itemListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found with id: " + listId));

        // Update fields
        existingList.setName(updatedList.getName());
        existingList.setPublic(updatedList.isPublic());
        // Add more fields to update if needed (e.g., items, description)

        return itemListRepository.save(existingList);
    }

    public void deleteList(String listId) {
        itemListRepository.deleteById(listId);
    }

    public Item getItemById(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
    }

    public List<ItemList> getRandomListsNotBelongingToUser(String userId) {
        return itemListRepository.findRandomListsNotBelongingToUser(userId);
    }



}
