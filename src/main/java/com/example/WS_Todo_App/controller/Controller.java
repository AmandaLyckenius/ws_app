package com.example.WS_Todo_App.controller;

import com.example.WS_Todo_App.model.Task;
import com.example.WS_Todo_App.model.TaskUser;
import com.example.WS_Todo_App.repository.TaskRepository;
import com.example.WS_Todo_App.repository.TaskUserRepository;
import com.example.WS_Todo_App.repository.TitleCount;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @RequestParam @NotBlank @Size(min=2, max=100) String username) {

        task.setUserId(username.trim());

        Task save=repo.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = repo.findAll();

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable ("username") @NotBlank @Size(min=2, max=100) String username) {
        List<Task> tasks = repo.findByUserId(username);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> getTasksByTitle(@RequestParam @NotBlank @Size(min= 2, max= 100) String title) {
        List<Task> tasks = repo.findByTitle(title);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Task> deleteTaskByTitle(@RequestParam @NotBlank @Size(min=2, max=100) String username, @RequestParam @NotBlank @Size(min=2, max= 100) String title){

        List<Task> task = repo.findByUserIdAndTitle(username.trim(), title.trim());

        if (task.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Task existingTask = task.get(0);
        repo.delete(existingTask);

        return ResponseEntity.ok(existingTask);
    }

    @PatchMapping("/updateDescription")
    public ResponseEntity<Task> updateDescription(@RequestParam @NotBlank @Size(min=2, max=100) String username, @RequestParam @NotBlank @Size(min=2, max=100) String title,
                                                  @RequestBody @NotBlank @Size(max=500) String newDescription){
        List<Task> task = repo.findByUserIdAndTitle(username.trim(), title.trim());

        if (task.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Task existingTask = task.get(0);
        existingTask.setDescription(newDescription.trim());
        Task taskUpdated = repo.save(existingTask);

        return ResponseEntity.ok(taskUpdated);
    }

    @PatchMapping("/setDone")
    public ResponseEntity<Task> updateTaskDone(@RequestParam @NotBlank @Size(min=2, max=100) String username, @RequestParam @NotBlank @Size(min=2, max=100) String title){
       List<Task> task = repo.findByUserIdAndTitle(username.trim(), title.trim());
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task taskToMark = task.get(0);
        if (!taskToMark.isDone()){
            taskToMark.setDone(true);
            repo.save(taskToMark);
            return ResponseEntity.ok(taskToMark);
        }

        taskToMark.setDone(false);
        repo.save(taskToMark);
        return ResponseEntity.ok(taskToMark);

    }


    @GetMapping("/top-common")
    public ResponseEntity<List<TitleCount>> getTopCommon(@RequestParam(defaultValue = "5")  @Min(1) @Max(100) int limit){

        List<TitleCount> result = repo.findTopNTitles(limit);

        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(result);

    }


}

