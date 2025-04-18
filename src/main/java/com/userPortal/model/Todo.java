package com.userPortal.model;

import java.time.LocalDateTime;

public class Todo {
    private int id;
    private String userEmail;
    private String task;
    private String status; // "Pending", "In Progress", "Completed"
    private LocalDateTime createdAt;

    public Todo() {}

    public Todo(String userEmail, String task, String status, LocalDateTime createdAt) {
        this.userEmail = userEmail;
        this.task = task;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Todo(int id, String userEmail, String task, String status, LocalDateTime createdAt) {
        this.id = id;
        this.userEmail = userEmail;
        this.task = task;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
