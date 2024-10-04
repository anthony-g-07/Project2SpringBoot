package edu.csumb.project_2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "itemLists") // Collection for item lists
public class ItemList {
    @Id
    private String id; // List ID

    private String name; // wishlist name
    private String userId;
    private boolean isPublic;
    private List<Item> items = new ArrayList<>();


    public ItemList() {}

    public ItemList(String name, String userId, boolean isPublic) {
        this.name = name;
        this.userId = userId;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
