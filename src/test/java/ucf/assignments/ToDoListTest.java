package ucf.assignments;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoListTest extends ApplicationTest {

    private ToDoListController todolistController;

    @BeforeEach
    public void clean() {
        todolistController.taskList.getItems().clear();
        todolistController.allList.getItems().clear();
    }

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("todolist.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        this.todolistController = fxmlLoader.getController();
        stage.show();
        stage.toFront();
    }

    // 9
    @Test
    void should_addtask_todolist() {
        assertEquals(0, todolistController.allList.getItems().size());

        lookup("#titleTextField").queryAs(TextField.class).setText("Task 1");
        lookup("#datePicker").queryAs(DatePicker.class).setValue(LocalDate.now());

        final Button button = lookup("#addButton").queryAs(Button.class);
        button.fire();

        assertEquals(1, todolistController.allList.getItems().size());
    }

    // 10
    @Test
    void should_deletetask_todolist() {
        todolistController.taskList.getItems().add(new Task("Task 1", "2021-10-01"));
        assertEquals(1, todolistController.taskList.getItems().size());
        todolistController.taskList.getSelectionModel().select(0);

        final Button button = lookup("#deleteButton").queryAs(Button.class);
        button.fire();

        assertEquals(0, todolistController.taskList.getItems().size());
    }

    // 11
    @Test
    void should_editdescription_todolist() {
        todolistController.taskList.getItems().add(new Task("Task 1", "2021-10-01"));
        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("Task 1", todolistController.taskList.getItems().get(0).getDescription());

        final TableView<Task> table = lookup("#taskList").queryAs(TableView.class);
        table.getItems().get(0).setDescription("Task2");

        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("Task2", todolistController.taskList.getItems().get(0).getDescription());
    }

    // 12
    @Test
    void should_editduedate_todolist() {
        todolistController.taskList.getItems().add(new Task("Task 1", "2021-10-01"));
        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("2021-10-01", todolistController.taskList.getItems().get(0).getDueDate());

        final TableView<Task> table = lookup("#taskList").queryAs(TableView.class);
        table.getItems().get(0).setDueDate("2021-10-02");

        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("2021-10-02", todolistController.taskList.getItems().get(0).getDueDate());
    }

    // 13
    @Test
    void should_markcompleted_todolist() {
        todolistController.taskList.getItems().add(new Task("Task 1", "2021-10-01"));
        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("Due", todolistController.taskList.getItems().get(0).getCompleted());

        todolistController.taskList.getSelectionModel().select(0);
        final Button button = lookup("#markAsCompletedButton").queryAs(Button.class);
        button.fire();

        assertEquals(1, todolistController.taskList.getItems().size());
        assertEquals("Completed", todolistController.taskList.getItems().get(0).getCompleted());
    }

    // 14
    @Test
    void should_displayall() {
        TodoList list = new TodoList("todo");
        list.addTask(new Task("Task 1", "2021-10-01"));
        list.addTask(new Task("Task 2", "2021-10-02"));
        list.addTask(new Task("Task 3", "2021-10-02"));
        todolistController.setToDoList(list);
        todolistController.taskList.getItems().get(2).markCompleted();

        final TableView<Task> table = lookup("#taskList").queryAs(TableView.class);
        final Button button = lookup("#toggleTable").queryAs(Button.class);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                assertEquals("All", button.getText());
                assertEquals(3, table.getItems().size());
            }
        });
    }

    // 15
    @Test
    void should_displayCompleted() {
        final TableView<Task> table = lookup("#taskList").queryAs(TableView.class);
        TodoList list = new TodoList("todo");
        list.addTask(new Task("Task 1", "2021-10-01"));
        list.addTask(new Task("Task 2", "2021-10-02"));
        list.addTask(new Task("Task 3", "2021-10-02"));
        todolistController.setToDoList(list);
        todolistController.taskList.getItems().get(2).markCompleted();

        final Button button = lookup("#toggleTable").queryAs(Button.class);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                button.fire();
                assertEquals("Completed", button.getText());

                assertEquals(1, table.getItems().size());
            }
        });
    }

    // 16
    @Test
    void should_displayDue() {
        TodoList list = new TodoList("todo");
        list.addTask(new Task("Task 1", "2021-10-01"));
        list.addTask(new Task("Task 2", "2021-10-02"));
        list.addTask(new Task("Task 3", "2021-10-02"));
        todolistController.setToDoList(list);
        todolistController.taskList.getItems().get(2).markCompleted();

        final TableView<Task> table = lookup("#taskList").queryAs(TableView.class);

        final Button button = lookup("#toggleTable").queryAs(Button.class);
        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  button.fire();
                                  button.fire();
                                  assertEquals("Due", button.getText());

                                  assertEquals(2, table.getItems().size());
                              }
        });
    }
}
