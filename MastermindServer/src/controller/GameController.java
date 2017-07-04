package controller;

import boxes.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message_game.MessageColors;
import static message_game.MessageState.*;
import server_player.ServerPlayer;
import static main.Window.screenGameOver;

public class GameController implements Initializable, ControlledScreen {

    @FXML
    private Button answerButton;

    @FXML
    private Button circleButton1;

    @FXML
    private Button circleButton2;

    @FXML
    private Button circleButton3;

    @FXML
    private Button circleButton4;

    @FXML
    private AnchorPane anchor;

    @FXML
    private GamePanelController gamePanelControlController;

    public static final String BUTTON1 = "circleButton1";

    public static final String BUTTON2 = "circleButton2";

    public static final String BUTTON3 = "circleButton3";

    public static final String BUTTON4 = "circleButton4";

    private String color1, color2, color3, color4;

    private ServerPlayer server;

    private Thread serverThread;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9998;

    private MessageColors mySequence;

    private ScreensController myController;

    private MainScreenController mainController;

    private double guesses;
   
    
    private final double AMOUNT_OF_GUESSES = 10;

    @FXML
    public void circleClicked(ActionEvent e) {
        MyPopUp popup = new MyPopUp(this);
        Stage stage = (Stage) anchor.getScene().getWindow();

        if (e.getSource().equals(circleButton1)) {
            popup.showPopup(BUTTON1, stage);
        } else if (e.getSource().equals(circleButton2)) {
            popup.showPopup(BUTTON2, stage);
        } else if (e.getSource().equals(circleButton3)) {
            popup.showPopup(BUTTON3, stage);
        } else if (e.getSource().equals(circleButton4)) {
            popup.showPopup(BUTTON4, stage);
        }
    }

    @FXML
    public void answerHandle() {

        if (color1 == null || color2 == null || color3 == null || color4 == null) {
            AlertBox alert = new AlertBox();
            alert.display("Nie wybrano kolorow!");
        } else if(guesses <= AMOUNT_OF_GUESSES){
            MessageColors messageColors = new MessageColors(ANSWER, color1, color2, color3, color4,guesses);
            gamePanelControlController.setHint(messageColors, guesses);
            setDisable(true);
            guesses++;
            
            if(guesses == AMOUNT_OF_GUESSES ){
            	setDisable(true);
            }
            try {
                server.sendMessage(messageColors);
            } catch (IOException ex) {
                System.out.println("Sending message failed. IOException. \n");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guesses = 1.0;
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void closeGame(Stage stage) {
    }

    public void connectGame(MainScreenController mainController) {

        this.mainController = mainController;
        try {
            server = new ServerPlayer(HOST, PORT, this);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Cannot create new ServerPlayer.");
        }
        serverThread = new Thread(server);
        serverThread.start();
    }

    public void disconnectGame() {

        MessageColors msg = new MessageColors(LOGOUT);
        try {
            server.sendMessage(msg);
            server.stop();
            serverThread.join();
        } catch (IOException ex) {
            System.out.println("Sending message failed. IOException. \n ");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException.\n");
        }
    }

    public void setConnected() {
        setDisable(false);
    }

    public void setDisconnected() {
        setDisable(true);
        refresh();
    }

    public void setDisable(boolean ifDisable) {
        answerButton.setDisable(ifDisable);
        circleButton1.setDisable(ifDisable);
        circleButton2.setDisable(ifDisable);
        circleButton3.setDisable(ifDisable);
        circleButton4.setDisable(ifDisable);
    }

    public void refresh() {
        gamePanelControlController.refresh();
    }

    public void setGuess(MessageColors sequence) {
        if (checkSequence(sequence)) {
            setDisable(true);
            MessageColors win = new MessageColors(GAMEWIN);
            try {
                server.sendMessage(win);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error sending win message.\n");
            }
           // myController.setScreen(screenGameOver);
            disconnectGame();
            mainController.setGameOver();
            AlertBox box = new AlertBox();
            box.display("Correct guess! You lost!");
        }
        gamePanelControlController.setGuess(sequence);
    }

    public void setGameWin() {
        mainController.setGameWin();
    }

    public boolean checkSequence(MessageColors sequence) {
        return (sequence == mySequence);
    }

    public void setButton1Color(String color) {
        if (!color.equals("brak")) {
            circleButton1.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color1 = color;
        }
    }

    public void setButton2Color(String color) {
        if (!color.equals("brak")) {
            circleButton2.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);

            color2 = color;
        }
    }

    public void setButton3Color(String color) {
        if (!color.equals("brak")) {
            circleButton3.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color3 = color;
        }
    }

    public void setButton4Color(String color) {
        if (!color.equals("brak")) {
            circleButton4.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color4 = color;
        }
    }
}
