package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.Task;
import com.example.WS_Todo_App.repository.TaskRepository;
import com.example.WS_Todo_App.repository.TitleCount;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/tasks")
@Validated
public class Controller {
    private final TaskRepository repo;

    public Controller(TaskRepository repo) {
        this.repo = repo;
    }
    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @RequestParam String username) {

        if (username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        task.setUserId(username);

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

    @GetMapping("/firstFive")
    public ResponseEntity<List<Task>> getTopFiveTasks() {
        List <Task> firstFive = repo.findTopFive(PageRequest.of(0,5));

        if (firstFive.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(firstFive);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable ("id") String id){

        Optional<Task> optionalTask = repo.findById(id);

        if (optionalTask.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Task existingTask = optionalTask.get();
        repo.delete(existingTask);

        return ResponseEntity.ok(existingTask);
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<Task> updateDescription(@PathVariable ("id") String id, @Valid @RequestBody String newDescription){
        Optional<Task> optionalTask = repo.findById(id);

        if (optionalTask.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Task existingTask = optionalTask.get();
        existingTask.setDescription(newDescription);
        Task taskUpdated = repo.save(existingTask);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }

    @PatchMapping("/{id}/done")
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


    @GetMapping("/top-common")
    public ResponseEntity<List<TitleCount>> getTopCommon(@RequestParam(defaultValue = "5") @Min(1) @Max(100) int limit){
        List<TitleCount> result = repo.findTopNTitles(limit);

        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(result);

    }


}

