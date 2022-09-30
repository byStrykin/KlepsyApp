package com.example.klepsy;

public class Task {
    private String name;
    private String description, complete;
    private long creationDate, completeDate;

    Task(String name, String description, long creationDate) {
        this.name = name;
        this.description = description;
        this.complete = "false";
        this.creationDate = creationDate;
        this.completeDate = 0;
    }

    public String getName() {
        return this.name;
    }

    public String isComplete() {
        return complete;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getCompleteDate() {
        return completeDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }
}
