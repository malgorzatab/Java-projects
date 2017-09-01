package controller;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
	
public class MainScreenController implements Initializable, ControlScreen{
	
	private ScreensController myController;
	

	    @FXML
	    private TableColumn<?, ?> levelColumn;
	    @FXML
	    private TableColumn<?, ?> techIdColumn;
	    @FXML
	    private TableColumn<?, ?> firstNameColumn;
	    @FXML
	    private TableView<?> tableStudents;
	    @FXML
	    private TableView<?> tableTechnique;
	    @FXML
	    private TableColumn<?, ?> coachColumn;
	    @FXML
	    private Button loadButton;
	    @FXML
	    private TableColumn<?, ?> lastNameColumn;
	    @FXML
	    private TableColumn<?, ?> techColumn;
	    @FXML
	    private Button loadTechButton;
	    @FXML
	    private TableColumn<?, ?> idColumn;
	    @FXML
	    void loadStudents(ActionEvent event) {
	    }
	    @FXML
	    void loadTechniques(ActionEvent event) {
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			  Task<Void> task = new Task<Void>() {
		            @Override
		            public Void call() throws Exception {
		                return null;
		            }
		        };
		        Thread th = new Thread(task);
		        th.setDaemon(true);
		        th.start();
			
		}

		@Override
		public void setParentScreen(ScreensController screenPage) {
			// TODO Auto-generated method stub
			 myController = screenPage;
			
		}
		 /* @FXML
		    public void handleLogOut() {
		        myController.setScreen(MyApplication.screenLogIn);
		    }*/

	

}
