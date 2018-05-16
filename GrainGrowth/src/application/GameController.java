package application;
import board.BoardGrid;
import board.GameModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    @FXML private Button clearButton;
    @FXML private TextField xField;
    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private TextField yField;
    @FXML private TextField rField;
    @FXML private TextField nField;
    @FXML private Pane boardPane;
    @FXML private CheckBox PBCbox;
    @FXML private Button sizeButton;
    @FXML private BoardGrid board;

        private GameModel model;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new GameModel(board);
        board.setSize(10,10);

    }

        @FXML
         void startAction(ActionEvent action) {
        int n = Integer.parseInt(nField.getText().trim());
            model.randomLiveCells(n);
            startButton.setDisable(true);
            stopButton.setDisable(false);
            clearButton.setDisable(true);
            board.setBlocked(true);
           // model.oneIteration();


        }

        @FXML
         void stopAction(ActionEvent action) {
            startButton.setDisable(false);
            stopButton.setDisable(true);
            clearButton.setDisable(false);
            board.setBlocked(false);
        }


        @FXML
         void randAction(ActionEvent action) {
            model.randomLiveCells(6);
        }

        @FXML
         void clearAction(ActionEvent action) {
            model.clearBoard();
        }

    @FXML
    void resizeBoard(ActionEvent action) {
        int sizeX  = Integer.parseInt(xField.getText().trim());
        int sizeY = Integer.parseInt(yField.getText().trim());
        board.setSize(sizeX,sizeY);
    }

    @FXML
    void boundaryAction(ActionEvent action) {

    }

}
