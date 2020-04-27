package main.java.ua.nure.bogun.epammed.service;


import java.io.IOException;
import java.util.Properties;

public class PropertyWorker {
    public static String readProperty(String path, String property) {
        Properties prop = new Properties();
        String result = null;
        try {
            prop.load(
                    PropertyWorker.class.getClassLoader().getResourceAsStream(path)
            );
            result = prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void setProperty(String path, String property, String value) {
        Properties prop = new Properties();
        String result = null;
        try {

            prop.load(
                    PropertyWorker.class.getClassLoader().getResourceAsStream(path)
            );
            prop.setProperty(property,value);

            System.out.println(prop.getProperty(property));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
