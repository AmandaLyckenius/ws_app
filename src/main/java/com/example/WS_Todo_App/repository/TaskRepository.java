package com.example.WS_Todo_App.repository;

import com.example.WS_Todo_App.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByTitle(String title);

    @Query("{}")
    List<Task> findTopFive(Pageable pageable);
}
