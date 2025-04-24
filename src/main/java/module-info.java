module schdeck.main {
    requires com.github.kwhat.jnativehook;
    requires java.logging;
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.json;

    opens hu.szatomi.schdeck to javafx.fxml;

    exports hu.szatomi.schdeck;
}