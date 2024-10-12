package edu.csumb.project_2.controller;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.model.ItemList;
import edu.csumb.project_2.service.ItemListService;
import edu.csumb.project_2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;
    private ItemListService itemListService;

    @Autowired
    public ItemController(ItemService itemService, ItemListService itemListService) {
        this.itemService = itemService;
        this.itemListService = itemListService;
    }

    // Get all lists for a user
    @GetMapping("/lists")
    public ResponseEntity<List<ItemList>> getAllListsForUser(@RequestParam String userId) {
        List<ItemList> itemLists = itemListService.findListsByUserId(userId);
        return ResponseEntity.ok(itemLists);
    }

    // Get a specific list by ID
    @GetMapping("/lists/{listId}")
    public ResponseEntity<ItemList> getListById(@PathVariable String listId) {
        ItemList itemList = itemListService.getListById(listId);
        return ResponseEntity.ok(itemList);
    }

    // Create a new list for a user
    @PostMapping("/lists")
    public ResponseEntity<ItemList> createList(
            @RequestParam String userId,
            @RequestParam String name,
            @RequestParam boolean isPublic) {
        // Create the ItemList object from the request parameters
        ItemList itemList = new ItemList();
        itemList.setUserId(userId);
        itemList.setName(name);
        itemList.setPublic(isPublic);

        // Save the list using the service
        ItemList savedList = itemListService.saveItemList(itemList);

        return ResponseEntity.ok(savedList);
    }


    @PatchMapping("/lists/{listId}")
    public ResponseEntity<ItemList> updateListByParams(
            @PathVariable String listId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isPublic) {
        // Fetch the existing list
        ItemList existingItemList = itemListService.getListById(listId);
        // Update only the provided fields
        if (name != null) {
            existingItemList.setName(name);
        }
        if (isPublic != null) {
            existingItemList.setPublic(isPublic);
        }
        // Save the updated list
        ItemList updatedItemList = itemListService.saveItemList(existingItemList);
        return ResponseEntity.ok(updatedItemList);
    }

    // Delete a list by ID
    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable String listId) {
        itemListService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/lists/{listId}/add-existing-item")
    public ResponseEntity<?> addExistingItemToList(
            @PathVariable String listId,
            @RequestParam String itemId) {
        try {
            // Fetch the existing list
            ItemList itemList = itemListService.getListById(listId);

            // Fetch the existing item by itemId
            Item item = itemService.getItemById(itemId);

            // Add the item to the list
            itemList.getItems().add(item);

            // Save the updated list
            ItemList updatedItemList = itemListService.saveItemList(itemList);

            return ResponseEntity.ok(updatedItemList);
        } catch (RuntimeException  e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List or Item not found.");
        }
    }


    //Gets 10 random lists that don't belong to the user
    @GetMapping("/lists/random")
    public List<ItemList> getRandomLists(@RequestParam String userId) {
        return itemListService.getRandomListsNotBelongingToUser(userId);
    }

    // List all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        // Fetch all items directly from the item collection
        List<Item> allItems = itemService.getAllItems();

        return ResponseEntity.ok(allItems);
    }

    // Get a specific item by its ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable String itemId) {
        Item item = itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    // Add a new item to the "items" collection
    @PostMapping
    public ResponseEntity<Item> addItemToCollection(@RequestBody Item item) {
        Item savedItem = itemService.addItemToCollection(item);
        return ResponseEntity.ok(savedItem);
    }



    // Update an item by its ID
    @PatchMapping("/{itemId}")
    public ResponseEntity<Item> updateItemByParams(
            @PathVariable String itemId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String description) {
        // Fetch the existing item
        Item existingItem = itemService.getItemById(itemId);
        // Update only the provided fields
        if (name != null) {
            existingItem.setName(name);
        }
        if (url != null) {
            existingItem.setUrl(url);
        }
        if (price != null) {
            existingItem.setPrice(price);
        }
        if (description != null) {
            existingItem.setDescription(description);
        }
        if (imageUrl != null) {
            existingItem.setImageURL(imageUrl);
        }
        // Save the updated item
        Item updatedItem = itemService.saveItem(existingItem);
        return ResponseEntity.ok(updatedItem);
    }


    // Delete an item from a list
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Item> listItems(@RequestParam String search) {
        List<String> searchTerms = Arrays.asList(search.split(","));
        return itemService.searchItems(searchTerms);
    }

    @DeleteMapping("/lists/{listId}/remove-item/{itemId}")
    public ResponseEntity<?> removeItemFromList(
            @PathVariable String listId,
            @PathVariable String itemId) {
        try {
            // Fetch the list by listId
            ItemList itemList = itemListService.getListById(listId);

            // Find and remove the item from the list
            List<Item> items = itemList.getItems();
            boolean removed = items.removeIf(item -> item.getId().equals(itemId));

            if (!removed) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in the list.");
            }

            // Save the updated list
            itemList.setItems(items);
            itemListService.saveItemList(itemList);

            return ResponseEntity.ok("Item removed from the list.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List or Item not found.");
        }
    }

}


