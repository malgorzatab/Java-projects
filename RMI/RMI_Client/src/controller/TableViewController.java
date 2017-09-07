package controller;

import student.StudentsHandling;
import student.Student;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewController implements Initializable {

    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> markColumn;
    @FXML private TableColumn<Student, String> ageColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    private StudentsHandling model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void loadTableView(String adress, StudentsHandling m) {
        this.model = m;
        this.model.loadData(new File(adress));
        tableView.getItems().setAll(model.getStudentList());
    }

    public void addStudent(StudentsHandling m, Student s) {
        this.model = m;
        if (!tableView.getItems().contains(s)) {
            tableView.getItems().add(s);
        }
    }

    public void addInputStudent(String adress, StudentsHandling m, Student s) {
        this.model = m;
        if (!tableView.getItems().contains(s)) {
            tableView.getItems().add(s);
            File f = new File(adress);
            this.model.saveData(f, s);
        }
    }

    public void deleteSelectedStudent(String adress, StudentsHandling m) {
        this.model = m;
        ObservableList<Student> selected, allStudents;

        allStudents = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();
        List<Student> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);

        File inputFile = new File(adress);
        String adress2 = "studentsTemp.txt";
        File tempFile = new File(adress2);

        this.model.deleteData(inputFile, tempFile, st);
    }

    public void deleteStudent(String adress, Student student) {
        if (tableView.getItems().contains(student)) {
            ObservableList<Student> allStudents = tableView.getItems();
            ObservableList<Student> remove = FXCollections.observableArrayList();
            remove.add(student);
            remove.forEach(allStudents::remove);
        }
    }
}
