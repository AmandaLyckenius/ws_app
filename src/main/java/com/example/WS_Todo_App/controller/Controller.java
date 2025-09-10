package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.Task;
import com.example.WS_Todo_App.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class Controller {
    private final TaskRepository repo;

    public Controller(TaskRepository repo) {
        this.repo = repo;
    }
    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task save=repo.save(task);
        return ResponseEntity.status(201).body(save);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = repo.findAll();

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> getTasksByTitle(@RequestParam String title) {
        List<Task> tasks = repo.findByTitle(title);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }
}

