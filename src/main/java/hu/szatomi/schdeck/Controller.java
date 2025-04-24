package hu.szatomi.schdeck;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @FXML public Node closeButton, minButton, settingsButton, notesButton, startButton, stopButton, autoClickerSettings;
    @FXML private Pane quick1, quick2, quick3, quick4;
    @FXML private AnchorPane header;

    @FXML private Text tempText, timeText, dateText;
    @FXML private ImageView indicator;

    Pane[] quicks;

    DayOfWeek today = LocalDateTime.now().getDayOfWeek();
    LocalTime currentTime;

    Weather weather;
    Popup popup;
    AutoClicker autoClicker = new AutoClicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Utils.setup(Main.stage, header, closeButton, minButton);
        updateTime();
        autoClicker.setupButtons(startButton, stopButton);

        quicks = new Pane[] {quick1, quick2, quick3, quick4};

        for (Pane pane : quicks) {
            setAnimation(pane);
        }

        setAnimation(settingsButton);
        setAnimation(autoClickerSettings);
        setAnimation(notesButton);
        setAnimation(startButton);
        setAnimation(stopButton);

        weather = new Weather(tempText);
        popup = new Popup();

        weather.start();
    }

    private double[] checkSides() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double x;

        if (screenSize.width - Main.stage.getX() >= 620) {
            x = Main.stage.getX() + 330;
        } else {
            x = Main.stage.getX() - 330;
        }

        double y = Main.stage.getY();

        return new double[] {x, y};
    }

    @FXML
    private void startAutoClicker() throws AWTException { AutoClicker.start(); }

    @FXML
    private void stopAutoClicker() { AutoClicker.stop();}

    @FXML
    private void openAutoClicker() { popup.open(checkSides()[0], checkSides()[1], "Auto clicker", "autoclicker"); }

    @FXML
    private void openNotes() { popup.open(checkSides()[0], checkSides()[1], "Notes", "notes"); }

    @FXML
    private void settings() { popup.open(checkSides()[0], checkSides()[1], "Settings", "settings"); }

    @FXML
    private void runCommand(String command) {
        command = command.replace("BROWSER", Config.getProperty("BROWSER"));

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.inheritIO();
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void openQuick1() { runCommand(Config.getProperty("QUICK1")); }

    @FXML
    private void openQuick2() { runCommand(Config.getProperty("QUICK2")); }

    @FXML
    private void openQuick3() { runCommand(Config.getProperty("QUICK3")); }

    @FXML
    private void openQuick4() { runCommand(Config.getProperty("QUICK4")); }

    private void setAnimation(Node button) {

        button.setOnMouseEntered(e -> {

            ScaleTransition scaleUp = new ScaleTransition(javafx.util.Duration.millis(50), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.play();
        });

        button.setOnMouseExited(e -> {

            ScaleTransition scaleDown = new ScaleTransition(javafx.util.Duration.millis(50), button);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);
            scaleDown.play();
        });
    }

    public void updateTime() {

        updateDay();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        timeText.setText(formatter.format(LocalTime.now()));
        currentTime = LocalTime.now();
        if (today != LocalDate.now().getDayOfWeek()) {
            updateDay();
        }

        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            timeText.setText(formatter.format(LocalTime.now()));
            currentTime = LocalTime.now();
            if (today != LocalDate.now().getDayOfWeek()) {
                updateDay();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateDay() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        dateText.setText(formatter.format(LocalDate.now()));
        today = LocalDate.now().getDayOfWeek();

    }
}
