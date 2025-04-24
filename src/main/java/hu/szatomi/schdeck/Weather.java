package hu.szatomi.schdeck;

import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Weather {

    Text tempText;
    private static double lastTemperature = Double.NaN;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Weather(Text tempText) {
        this.tempText = tempText;
    }

    public void fetchTemperature() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Tiszaf%C3%B6ldv%C3%A1r?unitGroup=metric&include=current&key=JXNKTJJ4HE2WVAKJSG49HCGBK&contentType=json"))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response;

        try {

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONObject jsonObject = new JSONObject(responseBody);

            double temperature = jsonObject.getJSONObject("currentConditions").getDouble("temp");

            if (Double.isNaN(lastTemperature) || temperature != lastTemperature) {

                tempText.setText((int) temperature + " °C");
                lastTemperature = temperature;

            } else {

                tempText.setText((int) temperature + " °C");
            }

        } catch (InterruptedException | IOException e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }

    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::fetchTemperature, 0, 1, TimeUnit.HOURS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
