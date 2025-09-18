package com.example.WS_Todo_App.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

    private boolean done;

    private String userId;

    public Task() {
    }

    public Task(String id, String title, String description, boolean done, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done=done;
        this.userId = userId;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
