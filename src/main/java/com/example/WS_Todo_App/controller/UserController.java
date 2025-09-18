package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.TaskUser;
import com.example.WS_Todo_App.repository.TaskUserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final TaskUserRepository taskUserRepo;

    public UserController(TaskUserRepository taskUserRepo) {
        this.taskUserRepo = taskUserRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskUser> createUser(@Valid @RequestBody TaskUser user) {
        if (taskUserRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        TaskUser saved = taskUserRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskUser>> getAllUsers() {
        List<TaskUser> users = taskUserRepo.findAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TaskUser> getUserByUsername(@PathVariable @NotBlank @Size(min=2, max=100) String username) {
        return taskUserRepo.findByUsername(username).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
