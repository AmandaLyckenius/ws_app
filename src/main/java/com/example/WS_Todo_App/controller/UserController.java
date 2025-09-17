package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.TaskUser;
import com.example.WS_Todo_App.repository.TaskUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final TaskUserRepository taskUserRepo;

    public UserController(TaskUserRepository taskUserRepo) {
        this.taskUserRepo = taskUserRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody TaskUser user) {
        if (taskUserRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        TaskUser saved = taskUserRepo.save(user);
        return ResponseEntity.status(201).body(saved);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskUser>> getAllUsers() {
        List<TaskUser> users = taskUserRepo.findAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TaskUser> getUserByUsername(@PathVariable String username) {
        return taskUserRepo.findByUsername(username).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
