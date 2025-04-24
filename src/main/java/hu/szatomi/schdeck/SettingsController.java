package hu.szatomi.schdeck;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

	@FXML private Node closeButton;
	@FXML private Node header;

	@FXML private ChoiceBox<String> browsers;

	public void initialize(URL url, ResourceBundle resourceBundle) {

		Utils.setup(Popup.popup, header, closeButton, null);

		browsers.getItems().setAll("chrome", "edge", "opera", "brave", "firefox");
		browsers.setValue(Config.getProperty("BROWSER"));
		browsers.setOnAction(this::changeBrowser);

	}

	public void changeBrowser(ActionEvent e) {

		Config.setProperty("BROWSER", browsers.getValue());
	}
}
