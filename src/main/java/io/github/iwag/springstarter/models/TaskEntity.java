package io.github.iwag.springstarter.models;

public class TaskEntity {
    public static final String ID = "id";
    public static final String UNTIL_DATE = "untilDate";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";

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

    private TaskEntity(Builder builder) {
        this.id = String.valueOf(builder.id);
        this.description = builder.description;
        this.priority = builder.priority;
        this.untilDate = builder.untilDate;
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

    public static class Builder {
        private Long id;
        private String description;
        private Integer priority;
        private String untilDate;

        public Builder title(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder untilDate(String untilDate) {
            this.untilDate = untilDate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskEntity build() {
            return new TaskEntity(this);
        }
    }
}
