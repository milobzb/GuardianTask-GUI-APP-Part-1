package ucf.assignments;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeTest extends ApplicationTest {

    private HomeController homeController;

    @BeforeEach
    public void clean() {
        homeController.todoList.getItems().clear();
    }

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        this.homeController = fxmlLoader.getController();
        stage.show();
        stage.toFront();
    }

    // 6
    @Test
    void should_add_todolist() {
        assertEquals(0, homeController.todoList.getItems().size());

        lookup("#toDoListTextField").queryAs(TextField.class).setText("TodoList 1");

        final Button button = lookup("#addButton").queryAs(Button.class); // requires your button to be tagged with setId("button")
        button.fire();

        assertEquals(1, homeController.todoList.getItems().size());
    }

    // 7
    @Test
    void should_remove_todolist() {
        homeController.todoList.getItems().add(new TodoList("List"));
        assertEquals(1, homeController.todoList.getItems().size());
        homeController.todoList.getSelectionModel().select(0);

        final Button button = lookup("#deleteButton").queryAs(Button.class); // requires your button to be tagged with setId("button")
        button.fire();

        assertEquals(0, homeController.todoList.getItems().size());
    }

    // 8
    @Test
    void should_editTitle_todolist() {
        homeController.todoList.getItems().add(new TodoList("Old Title"));
        assertEquals(1, homeController.todoList.getItems().size());
        assertEquals("Old Title", homeController.todoList.getItems().get(0).getTitle());

        assertEquals(1, homeController.todoList.getItems().size());
        homeController.todoList.getSelectionModel().select(0);

        final TableView<TodoList> table = lookup("#todoList").queryAs(TableView.class);
        table.getItems().get(0).setTitle("New Title");

        assertEquals(1, homeController.todoList.getItems().size());
        assertEquals("New Title", homeController.todoList.getItems().get(0).getTitle());
    }

    private <T extends Node> T find(String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

    // 17, 19
    @Test
    void should_save_load() {
        assertEquals(0, homeController.todoList.getItems().size());

        lookup("#toDoListTextField").queryAs(TextField.class).setText("testcase1");
        final Button button = lookup("#addButton").queryAs(Button.class); // requires your button to be tagged with setId("button")
        button.fire();

        lookup("#toDoListTextField").queryAs(TextField.class).setText("testcase2");
        button.fire();

        homeController.saveTodoList();
        homeController.loadTodoList();

        assertEquals(2,homeController.todoList.getItems().size());
    }

}
