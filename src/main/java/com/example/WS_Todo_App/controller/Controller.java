package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.Task;
import com.example.WS_Todo_App.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable ("id") String id){

        Optional<Task> optionalTask = repo.findById(id);

        if (optionalTask.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Task existingTask = optionalTask.get();
        repo.delete(existingTask);

        return ResponseEntity.status(HttpStatus.OK).body(existingTask);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateDescription(@PathVariable ("id") String id, @RequestBody String newDescription){
        Optional<Task> optionalTask = repo.findById(id);

        if (optionalTask.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Task existingTask = optionalTask.get();
        existingTask.setDescription(newDescription);
        Task taskUpdated = repo.save(existingTask);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTaskDone(@PathVariable ("id") String id){
        Optional<Task> optionalTask = repo.findById(id);

        if (optionalTask.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Task taskToMark = optionalTask.get();
        if (!taskToMark.isDone()){
            taskToMark.setDone(true);
            repo.save(taskToMark);
            return ResponseEntity.status(HttpStatus.OK).body(taskToMark);
        }

        taskToMark.setDone(false);
        repo.save(taskToMark);
        return ResponseEntity.status(HttpStatus.OK).body(taskToMark);

    }


}

