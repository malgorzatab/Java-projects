package controller;

import boxes.ConfirmBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static main.Window.screenMenu;
import static main.Window.screenGameWin;
import static main.Window.screenGameOver;
import message_communicator.MessageLogger;
import message_communicator.MessageModel;
import static message_communicator.MessageType.*;
import server.Server;

public class MainScreenController implements Initializable, ControlledScreen {

    @FXML
    private VBox vBox;

    @FXML
    private MessageBoxController messageBoxControlController;

    @FXML
    private GameController gameControlController;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    private Stage window;

    private ScreensController myController;

    private Server server;

    private Thread serverThread;

    private MessageLogger msgLogger;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9999;

    private String serverName;

    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    @FXML
    public void handleClose() {
        closeProgram();
    }

    @FXML
    public void backMenuHandle() {

        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Game interruption", "Sure you want to exit the game?");
        if (answer) {
            myController.setScreen(screenMenu);
            MessageModel msg = new MessageModel(this.serverName, LOGOUT, " has been logged out. \n");
            try {
                server.sendMessage(msg);
                server.stop();
                serverThread.join();
            } catch (IOException ex) {
                System.out.println("IOException.\n");
            } catch (InterruptedException e) {
                System.out.println("InteruptedException.\n");
            }
        }
    }

    @FXML
    public void sendHandle() {

        if (server.getSocket() != null) {

            String message = messageField.getText();
            MessageModel msg = new MessageModel(this.serverName, MESSAGE, message);
            msgLogger.log(msg);
            try {
                server.sendMessage(msg);
            } catch (IOException ex) {
                System.out.println("Sending message failed.\n");
            }
        } else {
            System.out.println("Cannot get socket channel of the client.\n");
        }
        messageField.clear();
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void closeGame(Stage stage) {
        this.window = stage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setLogged() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
        gameControlController.setDisable(true);
        connectCommunicator();
        connectGame();
    }

    public void setConnected() {
        messageField.setDisable(false);
        sendButton.setDisable(false);
    }

    public void setDisconnected() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
    }

    private void connectCommunicator() {
        msgLogger = new MessageLogger(this);
        this.serverName = "Server player";
        try {
            server = new Server(HOST, PORT, this, this.serverName);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Cannot create new Server.");
        }
        serverThread = new Thread(server);
        serverThread.start();
    }

    private void connectGame() {
        gameControlController.connectGame(this);
    }

    public void setText(String message) {
        messageBoxControlController.setText(message);
    }

    public void setGameOver() {
       // try {
            server.stop();
            //serverThread.join();
            myController.setScreen(screenGameOver);        
      /*  } catch (InterruptedException e) {
            System.out.println("InterruptedException.\n");
        }*/
    }

    public void setGameWin() {
       // try {
            server.stop();
           // serverThread.join();
            myController.setScreen(screenGameWin);
      /*  } catch (InterruptedException e) {
            System.out.println("InterruptedException.\n");
        }*/
    }

    public void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            MessageModel msg = new MessageModel(this.serverName, LOGOUT, " has been logged out.");
            try {
                System.out.println("0");
                server.sendMessage(msg);
                server.stop();
                System.out.println("1");
                gameControlController.disconnectGame();
                System.out.println("2");
                serverThread.join();
                System.out.println("3");
            } catch (IOException ex) {
                MessageModel error = new MessageModel(this.serverName, ERROR, "Sending message failed.\n ");
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException.\n");
                ex.printStackTrace();
            }
            this.window.close();
        }
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        vBox.getScene().setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(true);
            }
        });
    }

    public void handleEscReleased() {
        vBox.getScene().setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(false);
            }
        });
    }
}
