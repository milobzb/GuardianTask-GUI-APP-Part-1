package ucf.assignments;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class HomeController implements Initializable {

    @FXML
    protected Button addButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button modifyButton;

    @FXML
    protected TextField toDoListTextField;

    @FXML
    protected TableView<TodoList> todoList;

    @FXML
    protected TableColumn<TodoList, String> col_title;

    @FXML
    protected void addHandler(ActionEvent e) {
        //create new todolist by getting values from gui
        var newList = new TodoList(toDoListTextField.getText());

        todoList.getItems().add(newList);

//        saveTodoList();

        //reset fields
        toDoListTextField.setText("");
    }

    @FXML
    protected void deleteHandler(ActionEvent e) {
        TodoList selectedItem = (TodoList) todoList.getSelectionModel().getSelectedItem();
        todoList.getItems().remove(selectedItem);

//        saveTodoList();
    }

    @FXML
    protected void modifyHandler(ActionEvent e) throws IOException {
        TodoList selectedItem = (TodoList) todoList.getSelectionModel().getSelectedItem();
        App.todoLists.clear();
        App.todoLists.addAll(todoList.getItems());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        // Swap screen
        stage.setScene(new Scene(App.loadToDoListView(selectedItem)));

//        saveTodoList();
    }

    @FXML
    protected void onSave(ActionEvent e) {
        saveTodoList();
    }

    @FXML
    protected void onOpen(ActionEvent e) {
        loadTodoList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_title.setEditable(true);
        col_title.setCellFactory(TextFieldTableCell.forTableColumn());
        col_title.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<TodoList, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<TodoList, String> t) {
                        ((TodoList) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                    }
                }
        );
        todoList.getItems().addAll(App.todoLists);
//        loadTodoList();
    }

    public void loadTodoList() {
        todoList.getItems().clear();
        try {
            FileReader fr = new FileReader("todolist.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while((line = br.readLine()) != null)
            {
                var newList = new TodoList(line);
                todoList.getItems().add(newList);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveTodoList() {
        // whenever add new list, save it to external storage
        List<TodoList> list = todoList.getItems();
        try {
            FileWriter writer = new FileWriter("todolist.txt");

            for (int i = 0; i < list.size(); i++) {
                TodoList item = list.get(i);
                writer.write(item.getTitle() + "\n");
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
