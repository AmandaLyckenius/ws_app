package com.example.WS_Todo_App.repository;

import com.example.WS_Todo_App.model.TaskUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskUserRepository extends MongoRepository<TaskUser, String> {
    Optional<TaskUser> findByUsername(String username);
}
