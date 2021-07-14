package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Task implements Serializable {

    private SimpleStringProperty description;
    private SimpleStringProperty dueDate;
    private SimpleStringProperty completed;

    public Task(String description, String dueDate) {
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.completed = new SimpleStringProperty("Due");
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String deadline1) {
        description.set(deadline1);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(String dueDate) {
        this.dueDate = new SimpleStringProperty(dueDate);
    }

    public String getCompleted() {
        return completed.get();
    }

    public void markCompleted() {
        this.completed = new SimpleStringProperty("Completed");
    }
}
