package hu.szatomi.schdeck;

import java.io.*;
import java.util.Properties;

public class Config {

    private static final Properties config = new Properties();

    static {

        try (InputStream configFile = new FileInputStream(new File(System.getProperty("user.dir"), "config.properties"))) {
            config.load(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) { return config.getProperty(key); }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
        saveProperties();
    }

    private static void saveProperties() {

        File dir = new File(System.getProperty("user.dir"));

        File file = new File(dir, "config.properties");

        try (OutputStream output = new FileOutputStream(file)) {
            config.store(output, "Updated properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
