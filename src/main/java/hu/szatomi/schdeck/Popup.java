package hu.szatomi.schdeck;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Popup {

    static Stage stage;
    public static Stage popup;

    public void open(double x, double y, String name, String id) {

        try {

            popup = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource(id + ".fxml"));
            Scene scene = new Scene(root, 310, 330, Color.TRANSPARENT);

            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(stage);

            popup.setX(x);
            popup.setY(y);

            popup.setTitle(name);
            popup.getIcons().add(new Image("file:src/main/resources/hu/szatomi/schdeck/img/logo.png"));
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setResizable(false);
            popup.setScene(scene);

            popup.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
