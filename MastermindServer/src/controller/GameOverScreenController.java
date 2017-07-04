package controller;

import static main.Window.screenMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class GameOverScreenController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void goBackHandle(){
        myController.setScreen(screenMenu);
    }
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }
    
        @Override
    public void closeGame(Stage stage){}
}
