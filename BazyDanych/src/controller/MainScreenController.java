package controller;

//import boxes.AboutBox;


import boxes.AlertBox;
import static controller.LogInController.closeProgram;
//import crawler.Crawler;
//import student.StudentsHandling;
import main.MyApplication;
import model.StudentModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class MainScreenController implements Initializable, ControlScreen {

    private final String TEXTADRESS = "textLoggs.txt";
    private final String COMPRESSEDADRESS = "compressedLoggs.zip";
    private final String SERIALIZEDADRESS = "serializedLoggs.bin";
    private final String BINARYADRESS = "binaryLoggs.bin";
   /* public Logger[] loggers = new Logger[]{
        new GUILogger(this),
        new ConsoleLogger(),
        new TextLogger(TEXTADRESS),
        new CompressedLogger(COMPRESSEDADRESS),
        new SerializedLogger(SERIALIZEDADRESS),
        new BinaryLogger(BINARYADRESS)
    };*/
    private ScreensController myController;
    final BooleanProperty escPressed = new SimpleBooleanProperty(false);
    private StudentModel model = new StudentModel();
    @FXML private TextField markInput;
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField ageInput;
    @FXML private TableViewController tabViewControlController;
    @FXML private TextAreaController textAreaControlController;
  
    @FXML private BorderPane borderPane;
    @FXML private Menu menuFile;
    @FXML private MenuItem logOutMenuItem;
    @FXML private MenuItem closeMenuItem;
    @FXML private MenuBar menuBar;
    @FXML private Menu menuAbout;
    @FXML private MenuItem aboutMenuItem;
    @FXML private Tab studentsTab;
    @FXML private Tab loggsTab;
    @FXML private Tab histogramTab;
    
    private final String adress = "students.txt";

    public MainScreenController() {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* Crawler crawler = new Crawler(this);
        for (Logger l : loggers) {
            crawler.addNewStudentListener(l);
            crawler.addRemoveStudentListener(l);
            crawler.addUnchangedListener(l);
            crawler.addIterationStartedListener(l);
            crawler.addIterationComplitedListener(l);
        }*/

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
              //  crawler.run();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    public void initModel(StudentModel model) {
       /* if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;*/
    }

    @FXML
    public void handleLogOut() {
        myController.setScreen(MyApplication.screenLogIn);
    }

    @FXML
    public void handleClose() {
        MyApplication.closeProgram();
    }

  

    @FXML
    public void handleAddButton() {
        String mark = markInput.getText();
        String name = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String age = ageInput.getText();
        if (mark.trim().isEmpty() || name.trim().isEmpty() || lastName.trim().isEmpty() || age.trim().isEmpty() || !age.matches("\\d*") || !mark.matches("[2-4](\\.[0,5]{1,2}){0,1}|5(\\.0{1,2}){0,1}") /*|| name.matches("[0-9]")*/ || name.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || lastName.matches("[0-9]") || lastName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            AlertBox box = new AlertBox();
            box.display("Niepoprawne dane!");
        } else {
            StudentModel s = new StudentModel(Double.parseDouble(mark), name, lastName, Integer.parseInt(age));
          //  this.tabViewControlController.addInputStudent(adress, model, s);
        }
        markInput.clear();
        firstNameInput.clear();
        lastNameInput.clear();
        ageInput.clear();

    }

    @FXML
    public void handleDeleteButton() {
        this.tabViewControlController.deleteSelectedStudent(adress, model);
    }

    public void loadTableView() {
        this.tabViewControlController.loadTableView(adress, model);
    }

    public void setTextArea(String logg) {
        this.textAreaControlController.setTextArea(logg);
    }

   
    
    public void addRow(StudentModel student) {
        this.tabViewControlController.addStudent( student);
    }

    public void deleteRow(StudentModel student) {
        this.tabViewControlController.deleteStudent(adress, student);
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        borderPane.getScene().setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(true);
            }
        });
    }

    public void handleEscReleased() {
        borderPane.getScene().setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(false);
            }
        });
    }

}
