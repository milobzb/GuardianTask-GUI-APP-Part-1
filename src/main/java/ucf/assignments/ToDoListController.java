package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ToDoListController implements Initializable {

    @FXML
    protected Button addButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button markAsCompletedButton;

    @FXML
    protected Button backToHomeButton;

    @FXML
    protected Button toggleTable;

    @FXML
    protected TextField titleTextField;

    @FXML
    protected DatePicker datePicker;

    @FXML
    protected TableView<Task> taskList;

    protected TableView<Task> allList = new TableView<>();



    @FXML
    protected TableColumn<Task, String> col_description;

    @FXML
    protected TableColumn<Task, String> col_due_date;

    @FXML
    protected TableColumn<Task, String> col_completed;

    private TodoList m_todoList = null;

    @FXML
    protected void addHandler(ActionEvent e) {
        //create new todolist by getting values from gui
        var newTask = new Task(titleTextField.getText(), datePicker.getValue().toString());

        allList.getItems().add(newTask);

        //reset fields
        titleTextField.setText("");
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    protected void deleteHandler(ActionEvent e) {
        Task selectedItem = taskList.getSelectionModel().getSelectedItem();
        allList.getItems().remove(selectedItem);
        taskList.getItems().remove(selectedItem);
    }

    @FXML
    protected void markAsCompletedHandler(ActionEvent e) {
        Task selectedItem = taskList.getSelectionModel().getSelectedItem();
        taskList.getItems().remove(selectedItem);
        selectedItem.markCompleted();
        taskList.getItems().add(selectedItem);
    }

    @FXML
    protected void toggle() {
        if(toggleTable.getText().equals("All")) {
            taskList.setItems(allList.getItems().filtered((task) -> {
                return task.getCompleted().equals("Completed");
            }));
            toggleTable.setText("Completed");
        } else if (toggleTable.getText().equals("Completed")) {
            taskList.setItems(allList.getItems().filtered((task) -> {
                return task.getCompleted().equals("Due");
            }));
            toggleTable.setText("Due");
        } else {
            taskList.setItems(allList.getItems());
            toggleTable.setText("All");
        }
    }

    @FXML
    protected void onSave(ActionEvent e) {
        saveTaskList();
    }

    @FXML
    protected void onOpen(ActionEvent e) {
        loadTaskList();
    }

    public void loadTaskList() {
        allList.getItems().clear();
        try {
            FileReader fr = new FileReader( m_todoList.getTitle() + ".txt");
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String description = "";
            String due_date = "";
            String completed = "";
            int i = 0;
            while((line = br.readLine()) != null)
            {
                if( i % 3 == 0 ) // description
                    description = line;
                if( i % 3 == 1 ) // due date
                    due_date = line;
                if( i % 3 == 2 ) // completed
                {
                    completed = line;
                    var newTask = new Task(description, due_date);
                    if( completed.equals("Completed") )
                        newTask.markCompleted();

                    allList.getItems().add(newTask);
                }
                i++;
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(toggleTable.getText().equals("Completed")) {
            taskList.setItems(allList.getItems().filtered((task) -> {
                return task.getCompleted().equals("Completed");
            }));

        } else if (toggleTable.getText().equals("Due")) {
            taskList.setItems(allList.getItems().filtered((task) -> {
                return task.getCompleted().equals("Due");
            }));
        } else {
            taskList.setItems(allList.getItems());
            toggleTable.setText("All");
        }

    }

    public void saveTaskList() {
        // whenever add new list, save it to external storage
        List<Task> list = allList.getItems();
        try {
            FileWriter writer = new FileWriter(m_todoList.getTitle() + ".txt");

            for (int i = 0; i < list.size(); i++) {
                Task item = list.get(i);
                writer.write(item.getDescription() + "\n");
                writer.write(item.getDueDate() + "\n");
                writer.write(item.getCompleted() + "\n");
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        col_completed.setCellValueFactory(new PropertyValueFactory<>("completed"));
        col_description.setEditable(true);
        col_description.setCellFactory(TextFieldTableCell.forTableColumn());
        col_description.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }
        );

        col_due_date.setEditable(true);
        col_due_date.setCellFactory(TextFieldTableCell.forTableColumn());
        col_due_date.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDueDate(t.getNewValue());
                    }
                }
        );
    }

    public void setToDoList(TodoList list) {
        this.allList.setItems(list.getTasks());
        this.taskList.setItems(allList.getItems());

        m_todoList = list;
    }

    @FXML
    protected void backToHome(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        // Swap screen
        stage.setScene(new Scene(App.loadHome()));
    }

}
