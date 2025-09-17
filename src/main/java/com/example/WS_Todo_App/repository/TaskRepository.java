package com.example.WS_Todo_App.repository;

import com.example.WS_Todo_App.model.Task;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByTitle(String title);
    List<Task> findByUserIdAndTitle(String username, String title);


    @Query("{}")
    List<Task> findTopFive(Pageable pageable);

    @Aggregation(pipeline = {
            "{ $match: { title: { $ne: null, $ne: \"\" } } }",
            "{ $project: { normalizedTitle: { $toLower: { $trim: { input: \"$title\" } } } } }",
            "{ $group: { _id: \"$normalizedTitle\", count: { $sum: 1 } } }",
            "{ $sort: { count: -1, _id: 1 } }",
            "{ $limit: ?0 }",
            "{ $project: { _id: 0, title: \"$_id\", count: 1 } }"
    })
    List<TitleCount> findTopNTitles(int n);
}
