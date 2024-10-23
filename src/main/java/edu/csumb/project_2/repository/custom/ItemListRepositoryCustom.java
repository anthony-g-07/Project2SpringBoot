package edu.csumb.project_2.repository.custom;

import edu.csumb.project_2.model.ItemList;

import java.util.List;

public interface ItemListRepositoryCustom {
    List<ItemList> findRandomListsNotBelongingToUser(String userId);
}
