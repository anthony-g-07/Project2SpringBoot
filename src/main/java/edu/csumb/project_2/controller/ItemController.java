package edu.csumb.project_2.controller;

import edu.csumb.project_2.model.Item;
import edu.csumb.project_2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
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

    // List a specific item by search terms
    @GetMapping("/search")
    public ResponseEntity<List<Item>> listSpecificItem(@RequestParam String search) {
        String[] terms = search.split(",");
        // Logic to search for items using terms (to be implemented)
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    // Show a specific item by ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Optional<Item>> showSpecificItem(@PathVariable String itemId) {
        Optional<Item> item = itemService.getItemById(itemId);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

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




}
