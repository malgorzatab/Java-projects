package controller;

import static main.Window.screenMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameWinScreenController implements Initializable, ControlledScreen{

	  @FXML private Button goBackButton;
	  
	  ScreensController myController;
	
	@Override
	public void setParentScreen(ScreensController screenPage) {
		// TODO Auto-generated method stub
		
	}
	public void goBackHandle(){
        myController.setScreen(screenMenu);
    }

	@Override
	public void closeGame(Stage stage) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}



