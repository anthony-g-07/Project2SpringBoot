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

    @GetMapping
    public ResponseEntity<List<Item>> listAllItems(@RequestParam(required = false) String user,
                                                   @RequestParam(required = false) String list) {
        if (user == null || list == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Item> items = itemService.getItemByUser(user, list);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Item>> showSpecificList(@RequestParam String list) {
        List<Item> items = itemService.getItemsByListId(list);
        if (items == null || items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
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
    public ResponseEntity<Item> addItem(
            @RequestParam String item_name,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) String description
    ) {
        Item newItem = new Item();
        newItem.setName(item_name);
        newItem.setUrl(url);
        newItem.setPrice(price);
        newItem.setQuantity(quantity);
        newItem.setDescription(description);

        // Save the item
        Item savedItem = itemService.addItem(newItem);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteItem(@RequestParam String item_name) {
        boolean isDeleted = itemService.deleteItem(item_name);
        if (isDeleted) {
            return new ResponseEntity<>("Item successfully deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);

        }
    }




@GetMapping("/books")
public ResponseEntity<List<Item>> getBooks() {
    List<Item> books = itemService.getItemsByCategory("Books");
    return ResponseEntity.ok(books);
}


@PostMapping("/books")
public ResponseEntity<Item> addBook(@RequestBody Item item) {
    item.setCategory("Books");  // Ensure the category is set to "Books"
    Item savedItem = itemService.addItemToCollection(item);
    return ResponseEntity.ok(savedItem);
}



}


