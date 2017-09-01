package test;

import controller.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	 	public static String screenLogIn = "logIn";
	    public static String screenLogInFile = "/fxml/LogIn.fxml";
	    public static String screenSignUp = "signUp";
	    public static String screenSignUpFile = "/fxml/SignUp.fxml";
	    public static String screenMain = "myController";
	    public static String screenMainFile = "/fxml/MainScreen.fxml";
	    public static Stage window;
	    
  
	@Override
	public void start(Stage stage) throws Exception {
		 window = stage;
	     window.setTitle("Database");
	     window.setResizable(false);
	     
	     
	     ScreensController mainContainer = new ScreensController();

	        mainContainer.loadScreen(screenMain, screenMainFile);
	        mainContainer.loadScreen(screenLogIn, screenLogInFile);
	        mainContainer.loadScreen(screenSignUp, screenSignUpFile);

	        mainContainer.setScreen(screenLogIn);

	        Group root = new Group();
	        root.getChildren().add(mainContainer);
	        // Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

	        Scene scene = new Scene(root);
	        window.setScene(scene);
	        window.show();
		/*Parent root = FXMLLoader.load(getClass().getResource("/test/DatabaseFXML.fxml"));
		Scene scene =  new Scene(root);
		stage.setScene(scene);
		stage.show();*/
		
	        
	}
	
	

	public static void main(String[] args){
		
		launch(args);
		
		
	}

}
