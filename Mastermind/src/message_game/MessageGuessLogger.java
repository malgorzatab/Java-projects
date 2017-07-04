package message_game;

import controller.GameController;
import javafx.application.Platform;
import static message_game.MessageState.*;

public class MessageGuessLogger {

    private GameController controller;

    public MessageGuessLogger(GameController controller) {
        this.controller = controller;
    }

    public void log(MessageColors msg) {

        switch (msg.getState()) {
            case ANSWER:
            	if(msg.getCount_guess()<10){
                controller.setDisable(false);
            	}
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.setHint(msg);
                    }
                });
                
                break;
            case GAMEWIN:
                controller.setDisconnected();
                controller.setWinGame();
                break;
            case GAMEOVER:
                controller.setDisconnected();
                controller.setGameOver();
                break;
            case CONNECTED:
                controller.setConnected();
                break;
            case LOGOUT:
                controller.setDisconnected();
                break;
            default:
                break;
        }
    }
}
