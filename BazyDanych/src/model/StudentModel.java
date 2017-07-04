package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.StudentsParser;


public class StudentModel implements Serializable {

    private double mark;
    private String firstName;
    private String lastName;
    private int age;

    private final ObservableList<StudentModel> studentsList = FXCollections.observableArrayList(student
            -> new Observable[]{new SimpleDoubleProperty(student.getMark()), new SimpleStringProperty(student.getFirstName()), new SimpleStringProperty(student.getLastName()), new SimpleIntegerProperty(student.getAge())});

    public ObservableList<StudentModel> getStudentList() {
        return studentsList;
    }
    
    
    public StudentModel() {
    }

    public StudentModel(double mark, String firstName, String lastName, int age) {
        this.mark = mark;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public StudentModel(StudentModel s) {
        this.mark = s.getMark();
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.age = s.getAge();

    }
    
    public void loadData(File file) {
        try {
            List<StudentModel> students = StudentsParser.parse(file);
            for (StudentModel s : students) {
                studentsList.add(s);
            }
        } catch (IOException ex) {
        }
    }

    public void saveData(File file, StudentModel s) {

        String newLine = "\r\n";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge() + newLine);

        } catch (IOException e) {
            System.out.println("StudentsHandling saveData() - IOException");
        }

    }
    
    public void deleteData(File inputFile, File tempFile, List<StudentModel> st) {
        String lineToRemove;
        String currentLine;

        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            for (StudentModel s : st) {
                lineToRemove = s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge();

                while ((currentLine = reader.readLine()) != null) {
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.contains(lineToRemove)) {
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                inputFile.delete();
                Files.move(tempFile.toPath(), inputFile.toPath());
            }
        } catch (IOException ex) {
            System.out.println("StudentsHandling deleteData() - IOException");
        }

    }
    
    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("   " + this.firstName);
        result.append("   " + this.lastName);
        result.append("   " + this.mark);
        result.append("   " + this.age);
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.mark) ^ (Double.doubleToLongBits(this.mark) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.firstName);
        hash = 71 * hash + Objects.hashCode(this.lastName);
        hash = 71 * hash + this.age;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentModel other = (StudentModel) obj;
        if (Double.doubleToLongBits(this.mark) != Double.doubleToLongBits(other.mark)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }

}
