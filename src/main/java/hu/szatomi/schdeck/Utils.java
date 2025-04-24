package hu.szatomi.schdeck;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

    public static void close(Stage stage, Node closeButton) {

        closeButton.setCursor(Cursor.HAND);

        closeButton.setOnMouseReleased(e -> {

            if (stage == Main.stage) {
                System.exit(0);
            } else {
                stage.close();
            }
        });
    }

    public static void header(Stage stage , Node header) {

        final double[] x = {0};
        final double[] y = {0};

        header.setOnMousePressed(e -> {
            x[0] = e.getSceneX();
            y[0] = e.getSceneY();
        });

        header.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - x[0]);
            stage.setY(e.getScreenY() - y[0]);
        });
    }

    public static void minimize(Stage stage, Node minButton) {

        minButton.setCursor(Cursor.HAND);

        minButton.setOnMouseReleased(e -> {
            stage.setIconified(true);
        });
    }

    public  static void setup(Stage stage, Node header, Node closeButton, Node minButton) {

        header(stage, header);
        close(stage, closeButton);
        if (minButton != null) { minimize(stage, minButton); }
    }
}
