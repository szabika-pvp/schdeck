package hu.szatomi.schdeck;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage stage;

    public static void main(String[] args) {

        try {
            //System.load(new java.io.File("JNativeHook.dll").getAbsolutePath());
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("\nSTUPID FUCKING NIGGERS\n");
            e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(new HotkeyListener());

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        try {

            Main.stage = stage;

            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root, 310, 330, Color.TRANSPARENT);

            stage.setTitle("Schdeck");
            stage.getIcons().add(new Image("file:src/main/resources/hu/szatomi/schdeck/img/logo.png"));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}