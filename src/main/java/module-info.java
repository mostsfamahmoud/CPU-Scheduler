module Os_project.os_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires javafx.swing;


    opens Os_project to javafx.fxml;
    exports Os_project;
}