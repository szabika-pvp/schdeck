package hu.szatomi.schdeck;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class ClickerController implements Initializable {

    @FXML private Node closeButton;
    @FXML private Node header;
    @FXML private Node leftButton, rightButton;

    @FXML private TextField numberField;

    int maxLength = 5;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        Utils.setup(Popup.popup, header, closeButton, null);

        AutoClicker.clickButtons[0] = leftButton;
        AutoClicker.clickButtons[1] = rightButton;

        int buttonIndex = Integer.valueOf(Config.getProperty("CLICK_BUTTON"));
        AutoClicker.changeClick(buttonIndex);

        numberField.setText(Config.getProperty("CLICK_INTERVAL"));

        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(oldValue);
                Config.setProperty("CLICK_INTERVAL", oldValue);
            } else {
                Config.setProperty("CLICK_INTERVAL", newValue);
            }
        });

        numberField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                Platform.runLater(() -> {
                    numberField.positionCaret(numberField.getText().length());
                });
            }
        });

        numberField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= maxLength) {
                return change;
            } else {
                return null;
            }
        }));
    }

    public static void setClickInterval(int interval) {
        AutoClicker.setClickInterval(interval);
    }

    @FXML
    private void leftButtonClicked() {
        AutoClicker.changeClick(0);
    }

    @FXML
    private void rightButtonClicked() {
        AutoClicker.changeClick(1);
    }
}
