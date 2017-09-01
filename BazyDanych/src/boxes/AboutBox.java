package boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutBox {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("About me");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);

        Label label = new Label("Autor: Ma³gorzata Bieñ \nKierunek: Informatyka Stosowana \nRok: II \nWydzia³: IMiIP");
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
