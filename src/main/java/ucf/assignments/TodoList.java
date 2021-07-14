package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class TodoList implements Serializable {

    private SimpleStringProperty title;
    private final ObservableList<Task> tasks =
            FXCollections.observableArrayList();

    public TodoList(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
