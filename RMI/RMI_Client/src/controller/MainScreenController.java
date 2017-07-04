package controller;

import interfaces.Controller;
import boxes.AboutBox;
import boxes.AlertBox;
import boxes.ConfirmBox;
import main.MyApplication;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import static main.MyApplication.window;
import interfaces.Logger;
import java.rmi.RMISecurityManager;
import student.Student;
import student.StudentsHandling;
import interfaces.RMICrawlerProxyInterface;
import java.rmi.server.UnicastRemoteObject;
import logger.ConsoleLogger;
import logger.GUILogger;

public class MainScreenController extends UnicastRemoteObject implements Initializable, ControlScreen, Controller {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScreensController myController;

    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    private StudentsHandling model = new StudentsHandling();

    private final String HOST = "127.0.0.1";

    private final int PORT = 1099;
  
    @FXML private Menu menuFile;
    @FXML private MenuItem logOutMenuItem;
    @FXML private MenuItem closeMenuItem;
    @FXML private MenuBar menuBar;
    @FXML private Menu menuAbout;
    @FXML private MenuItem aboutMenuItem;
    @FXML private Tab studentsTab;
    @FXML private Tab loggsTab;
    @FXML private Tab histogramTab;
    @FXML private TabPane tabPane;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML
    private TextField markInput;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField ageInput;
    @FXML
    private TableViewController tabViewControlController;
    @FXML
    private TextAreaController textAreaControlController;
    @FXML
    private BorderPane borderPane;

    private final String adress = "students.txt";

    public Logger[] loggers = new Logger[]{
        new GUILogger(this),
        new ConsoleLogger()
    };

    public MainScreenController() throws RemoteException {
        super();
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      /*  if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
                        
        // nawiazywanie polaczenia
        String name = "rmi://"+PORT+"/CrawlerProxy";
        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT); // nawiazywanie polaczenia
            RMICrawlerProxyInterface crawler = (RMICrawlerProxyInterface) registry.lookup(name); // pobranie zbindowanej na serwerze logiki 
            crawler.setController(this);				//Returns the remote reference bound to the specified name in this registry.Parameters:name the name for the remote reference to look up Returns:a reference to a remote object

            for (Logger l : loggers) {
                crawler.addNewStudentListener(l);
                crawler.addRemoveStudentListener(l);
                crawler.addUnchangedListener(l);
                crawler.addIterationStartedListener(l);
                crawler.addIterationComplitedListener(l);
            }

            Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    crawler.run();
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();

        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("\nRemoteException was thrown in initialize.");
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.out.println("\nNotBoundException was thrown.");
        }
    }

    public void init(StudentsHandling model) {
        if (this.model != null) {
            throw new IllegalStateException(" model can only be initialized once");
        }
        this.model = model;
    }

    @FXML
    public void handleClose() {
        MyApplication.closeProgram();
    }

    @FXML
    public void handleAbout() {
        AboutBox.display();
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
            Student s = new Student(Double.parseDouble(mark), name, lastName, Integer.parseInt(age));
            this.tabViewControlController.addInputStudent(adress, model, s);
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

    @Override
    public void setTextArea(String logg) throws RemoteException {
        this.textAreaControlController.setTextArea(logg);
    }

    @Override
    public void addRow(Object student) throws RemoteException {
        this.tabViewControlController.addStudent(model, (Student) student);
    }

    @Override
    public void deleteRow(Object student) throws RemoteException {
        this.tabViewControlController.deleteStudent(adress, (Student) student);
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            try {
                closeProgram();
            } catch (RemoteException ex) {
                System.out.println("\nRemoteException was thrown.");
            }
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

    public static void closeProgram() throws RemoteException {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("  Close  ", "Do you want to exit?");
        if (answer) {
            window.close();
        }
    }

}
