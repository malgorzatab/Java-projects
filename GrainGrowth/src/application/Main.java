package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gameBoard.fxml"));
        primaryStage.setTitle("Game of life");
        Scene scene = new Scene(root, 800, 480);
        scene.getStylesheets().add("style/style.css");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(480);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }
}
