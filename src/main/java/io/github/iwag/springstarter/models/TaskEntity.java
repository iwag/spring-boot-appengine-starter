package io.github.iwag.springstarter.models;

public class TaskEntity {
    private String id;
    private String description;
    private Integer priority;
    private String untilDate;

    public TaskEntity(String id, String description, Integer priority, String untilDate) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.untilDate = untilDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(String untilDate) {
        this.untilDate = untilDate;
    }
}
