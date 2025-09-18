package com.example.WS_Todo_App.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "taskUser")
public class TaskUser {
    @Id
    private String id;

    @NotBlank
    @Size(min=3, max=32)
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 3, max = 32) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 32) String username) {
        this.username = username;
    }
}
