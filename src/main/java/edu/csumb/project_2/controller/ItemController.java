package edu.csumb.project_2.controller;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.model.ItemList;
import edu.csumb.project_2.service.ItemListService;
import edu.csumb.project_2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemListService itemListService;

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
        ItemList itemList = new ItemList();
        itemList.setUserId(userId);
        itemList.setName(name);
        itemList.setPublic(isPublic);

        ItemList savedList = itemListService.saveItemList(itemList);
        return ResponseEntity.ok(savedList);
    }

    @PatchMapping("/lists/{listId}")
    public ResponseEntity<ItemList> updateListByParams(
            @PathVariable String listId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isPublic) {
        ItemList existingItemList = itemListService.getListById(listId);

        if (name != null) {
            existingItemList.setName(name);
        }
        if (isPublic != null) {
            existingItemList.setPublic(isPublic);
        }

        ItemList updatedItemList = itemListService.saveItemList(existingItemList);
        return ResponseEntity.ok(updatedItemList);
    }

    // Delete a list by ID
    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable String listId) {
        itemListService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }

    // Add an existing item to a list
    @PostMapping("/lists/{listId}/add-existing-item")
    public ResponseEntity<?> addExistingItemToList(
            @PathVariable String listId,
            @RequestParam String itemId) {
        try {
            ItemList itemList = itemListService.getListById(listId);
            Item item = itemService.getItemById(itemId);
            itemList.getItems().add(item);

            ItemList updatedItemList = itemListService.saveItemList(itemList);
            return ResponseEntity.ok(updatedItemList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List or Item not found.");
        }
    }

    // Get 10 random lists that don't belong to the user
    @GetMapping("/lists/random")
    public List<ItemList> getRandomLists(@RequestParam String userId) {
        return itemListService.getRandomListsNotBelongingToUser(userId);
    }

    // List all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> allItems = itemService.getAllItems();
        return ResponseEntity.ok(allItems);
    }

    // Get a specific item by its ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable String itemId) {
        Item item = itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    // Add a new item to the collection
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item newItem) {
        Item savedItem = itemService.addItem(newItem);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // Add a new item specifically to the "Books" category
    @PostMapping("/books")
    public ResponseEntity<Item> addBook(@RequestBody Item item) {
        item.setCategory("Books");  // Ensure the category is set to "Books"
        Item savedItem = itemService.addItemToCollection(item);
        return ResponseEntity.ok(savedItem);
    }

    // Get books from the "Books" category
    @GetMapping("/books")
    public ResponseEntity<List<Item>> getBooks() {
        List<Item> books = itemService.getItemsByCategory("Books");
        return ResponseEntity.ok(books);
    }

   @PostMapping("/clothing")
public ResponseEntity<Item> addCloth(@RequestBody Item item) {
    // Set the category to Clothing
    item.setCategory("Clothing");
    // Save the item only to the Clothing collection
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}

@GetMapping("/clothing")
public ResponseEntity<List<Item>> getCloth() {
    // Fetch items from the Clothing category only
    List<Item> clothing = itemService.getItemsByCategory("Clothing");
    return ResponseEntity.ok(clothing);
}

@PostMapping("/gadget")
public ResponseEntity<Item> addGadget(@RequestBody Item item) {
    // Set the category to Gadget
    item.setCategory("Gadget");
    // Save the item only to the Gadget collection
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}

@GetMapping("/gadget")
public ResponseEntity<List<Item>> getGadget() {
    // Fetch items from the Gadget category only
    List<Item> gadgets = itemService.getItemsByCategory("Gadget");
    return ResponseEntity.ok(gadgets);
}


@PostMapping("/handcraft")
public ResponseEntity<Item> addHandCraft(@RequestBody Item item) {
    // Set the category to HandCraft
    item.setCategory("HandCraft");
    // Save the item only to the HandCraft collection
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}

@GetMapping("/handcraft")
public ResponseEntity<List<Item>> getHandCraft() {
    // Fetch items from the HandCraft category only
    List<Item> handcrafts = itemService.getItemsByCategory("HandCraft");
    return ResponseEntity.ok(handcrafts);
}


@PostMapping("/giftcard")
public ResponseEntity<Item> addGiftCard(@RequestBody Item item) {
    // Set the category to GiftCard
    item.setCategory("GiftCard");
    // Save the item only to the GiftCard collection
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}

@GetMapping("/giftcard")
public ResponseEntity<List<Item>> getGiftCard() {
    // Fetch items from the GiftCard category only
    List<Item> giftCards = itemService.getItemsByCategory("GiftCard");
    return ResponseEntity.ok(giftCards);
}






  @PostMapping("/games")
public ResponseEntity<Item> addGame(@RequestBody Item item) {
    // Set the category to Games
    item.setCategory("Games");
    // Save the item only to the Games collection
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}

@GetMapping("/games")
public ResponseEntity<List<Item>> getGames() {
    // Fetch items from the Games category only
    List<Item> games = itemService.getItemsByCategory("Games");
    return ResponseEntity.ok(games);
}




    // Delete an item by ID
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemId) {
        try {
            itemService.deleteItem(itemId);
            return new ResponseEntity<>("Item successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Item not found.", HttpStatus.NOT_FOUND);
        }
    }


    
    // Update an item by its ID
    @PatchMapping("/{itemId}")
    public ResponseEntity<Item> updateItemByParams(
            @PathVariable String itemId,
            @RequestBody Item updatedItem) {
        Item existingItem = itemService.getItemById(itemId);

        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getPrice() != null) {
            existingItem.setPrice(updatedItem.getPrice());
        }
        if (updatedItem.getUrl() != null) {
            existingItem.setUrl(updatedItem.getUrl());
        }
        if (updatedItem.getImageURL() != null) {
            existingItem.setImageURL(updatedItem.getImageURL());
        }

        Item updated = itemService.saveItem(existingItem);
        return ResponseEntity.ok(updated);
    }

    // Remove an item from a list
    @DeleteMapping("/lists/{listId}/remove-item/{itemId}")
    public ResponseEntity<?> removeItemFromList(
            @PathVariable String listId,
            @PathVariable String itemId) {
        try {
            ItemList itemList = itemListService.getListById(listId);
            List<Item> items = itemList.getItems();
            boolean removed = items.removeIf(item -> item.getId().equals(itemId));

            if (!removed) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in the list.");
            }

            itemList.setItems(items);
            itemListService.saveItemList(itemList);
            return ResponseEntity.ok("Item removed from the list.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List or Item not found.");
        }
    }

    // Search items based on search terms
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam String search) {
        return itemService.searchItems(search);
    }
}


