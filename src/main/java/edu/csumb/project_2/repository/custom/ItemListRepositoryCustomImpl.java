package edu.csumb.project_2.repository.custom;

import edu.csumb.project_2.model.ItemList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemListRepositoryCustomImpl implements ItemListRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public ItemListRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ItemList> findRandomListsNotBelongingToUser(String userId) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").ne(userId)),  // Find lists that don't belong to the user
                Aggregation.sample(10) // Randomly sample 10 lists
        );

        AggregationResults<ItemList> result = mongoTemplate.aggregate(agg, "itemLists", ItemList.class);
        return result.getMappedResults();
    }
}
