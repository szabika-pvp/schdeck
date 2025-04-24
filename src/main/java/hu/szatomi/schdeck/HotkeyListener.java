package hu.szatomi.schdeck;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;

public class HotkeyListener implements NativeKeyListener {

    private final AutoClicker autoClicker = new AutoClicker();

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_F6) {

            if (autoClicker.running) {
                autoClicker.stop();
            } else {
                try {
                    autoClicker.start();
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}
}
