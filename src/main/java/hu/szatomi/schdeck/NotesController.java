package hu.szatomi.schdeck;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesController implements Initializable {

    @FXML private Node closeButton;
    @FXML private Node header;
    @FXML private TextArea textArea;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        Utils.setup(Popup.popup, header, closeButton, null);

        textArea.setText(Config.getProperty("NOTES"));
        textArea.positionCaret(textArea.getText().length());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> saveNotes()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        closeButton.setOnMouseReleased(e -> {

            saveNotes();
            Popup.popup.close();
        });
    }

    public void saveNotes() {
        Config.setProperty("NOTES", textArea.getText());
    }
}
