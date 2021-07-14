module ucf.assignments {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens ucf.assignments to javafx.fxml;
    exports ucf.assignments;
}