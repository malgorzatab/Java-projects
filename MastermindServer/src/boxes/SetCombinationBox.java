package boxes;

import static boxes.ConfirmBox.answer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetCombinationBox {

    final BooleanProperty enterPressed = new SimpleBooleanProperty(false);

    public void display() {
        Stage window = new Stage();
        window.setTitle("Set your combination!");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);

        Label label = new Label("Choose colors, which your competitor will have to guess.");
        Button backButton = new Button("Continue");
        backButton.setOnAction(e -> window.close());

        HBox buttons = new HBox(10);
        
        Button button1 = new Button();
        button1.setStyle("    -fx-background-radius: 30em;"
                + "    -fx-min-width: 30px;"
                + "    -fx-min-height: 30px;"
                + "    -fx-max-width: 30px;"
                + "    -fx-max-height: 30px");
        
        Button button2 = new Button();
        button1.setStyle("    -fx-background-radius: 30em;"
                + "    -fx-min-width: 30px;"
                + "    -fx-min-height: 30px;"
                + "    -fx-max-width: 30px;"
                + "    -fx-max-height: 30px");
        
        Button button3 = new Button();
        button1.setStyle("    -fx-background-radius: 30em;"
                + "    -fx-min-width: 30px;"
                + "    -fx-min-height: 30px;"
                + "    -fx-max-width: 30px;"
                + "    -fx-max-height: 30px");
        
        Button button4 = new Button();
        button1.setStyle("    -fx-background-radius: 30em;"
                + "    -fx-min-width: 30px;"
                + "    -fx-min-height: 30px;"
                + "    -fx-max-width: 30px;"
                + "    -fx-max-height: 30px");
        
        buttons.getChildren().addAll(button1, button2, button3, button4);
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttons, backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);

        enterPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            answer = true;
            window.close();
        });

        scene.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                enterPressed.set(true);
            }
        });
        scene.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                enterPressed.set(false);
            }
        });

        window.showAndWait();
    }
}
