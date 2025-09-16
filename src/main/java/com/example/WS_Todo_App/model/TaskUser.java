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

}
