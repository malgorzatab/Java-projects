package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import logger.GUILogger;
import model.StudentModel;

public class TextAreaController implements Initializable {

    @FXML
    private TextArea textArea;
    GUILogger logger;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setTextArea(String Logg) {
        textArea.appendText(Logg);
    }

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	

}
