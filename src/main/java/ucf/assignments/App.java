package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bahr
 */
public class App extends Application {

    public static List<TodoList> todoLists = new ArrayList<>();
    private static Scene scene;
    public static Stage stage1;

    @Override
    public void start(Stage stage) throws IOException {
        todoLists = new ArrayList<>();
        scene = new Scene(loadHome());
        stage.setScene(scene);
        stage1 = stage;
        stage.show();
    }

    public static Parent loadHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Parent parent = fxmlLoader.load();
        return parent;
    }

    public static Parent loadToDoListView(TodoList list) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("todolist.fxml"));
        Parent parent = fxmlLoader.load();
        ToDoListController toDoListController = fxmlLoader.getController();
        toDoListController.setToDoList(list);
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }

}

