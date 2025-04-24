package hu.szatomi.schdeck;

import javafx.scene.Node;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;

public class AutoClicker {

    public static boolean running = false;

    private static Timer clickTimer;
    private static Robot robot;
    private static int clickInterval;

    static Node startButton, stopButton;

    private static int click = 0;
    static Node[] clickButtons = new Node[2];

    public void setupButtons(Node startButton, Node stopButton) {
        this.startButton = startButton;
        this.stopButton = stopButton;
    }

    static void start() throws AWTException {

        robot = new Robot();

        clickInterval = Integer.parseInt(Config.getProperty("CLICK_INTERVAL"));
        int button = click == 0 ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;

        clickTimer = new Timer();
        clickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                robot.mousePress(button);
                robot.mouseRelease(button);
            }
        }, 0, clickInterval);

        running = true;

        startButton.setDisable(true);
        stopButton.setDisable(false);
        startButton.setOpacity(0.5);
        stopButton.setOpacity(1);
    }

    static void stop() {
        if (clickTimer != null) {
            clickTimer.cancel();
        }

        running = false;

        stopButton.setDisable(true);
        startButton.setDisable(false);
        stopButton.setOpacity(0.5);
        startButton.setOpacity(1);
    }

    static void changeClick(int idx) {

        click = idx;
        Config.setProperty("CLICK_BUTTON", String.valueOf(click));

        clickButtons[1 - idx].setOpacity(0.5);
        clickButtons[idx].setOpacity(1);
    }

     public static void setClickInterval(int interval) { clickInterval = interval; }
}
