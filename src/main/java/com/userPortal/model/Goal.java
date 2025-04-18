package com.userPortal.model;

import java.sql.Date;

public class Goal {
    private int goalId;
    private String userEmail;  // Changed from int userId
    private String title;
    private String description;
    private String category;
    private Date targetDate;
    private String priority;
    private int progress;
    
    // ===== Constructors =====
    public Goal() {}

    public Goal(int goalId, String userEmail, String title, String description, 
                String category, Date targetDate, String priority, int progress) {
        this.goalId = goalId;
        this.userEmail = userEmail;
        this.title = title;
        this.description = description;
        this.category = category;
        this.targetDate = targetDate;
        this.priority = priority;
        this.progress = progress;
    }

    // ===== Getters & Setters =====
    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    // ===== Utility Methods =====
    @Override
    public String toString() {
        return "Goal [goalId=" + goalId + ", userEmail=" + userEmail + ", title=" + title + 
               ", progress=" + progress + "%]";
    }
}