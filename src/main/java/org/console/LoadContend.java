package org.console;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadContend {
    private static Properties prop;
    public static void LoadContendStart() {
        prop = new Properties();
        String fileName = "app.config";
        try (
                FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (
                FileNotFoundException ex) {
            // FileNotFoundException catch is optional and can be collapsed
        } catch (
                IOException ex) {
        }
        setDisplayContend();
    }

    public static void setDisplayContend() {
        switch (Main.getLayer()) {
            case 1:
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.1"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.2"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.3"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.4"));
                break;
            case 2:
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.5"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.6"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.7"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.8"));
                break;
            case 3:
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.9"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.10"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.11"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.12"));
                break;
            case 4:
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.13"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.14"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.15"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.16"));
                break;
            case 5:
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.17"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.18"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.19"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.20"));
                break;
        }
    }
    public static void setDisplayDefault() {
        switch (Main.getLayer()) {
            case 1:
                Main.getFaderWindow().changeLable1("channel 1");
                Main.getFaderWindow().changeLable2("channel 2");
                Main.getFaderWindow().changeLable3("channel 3");
                Main.getFaderWindow().changeLable4("channel 4");
                break;
            case 2:
                Main.getFaderWindow().changeLable1("channel 5");
                Main.getFaderWindow().changeLable2("channel 6");
                Main.getFaderWindow().changeLable3("channel 7");
                Main.getFaderWindow().changeLable4("channel 8");
                break;
            case 3:
                Main.getFaderWindow().changeLable1("channel 9");
                Main.getFaderWindow().changeLable2("channel 10");
                Main.getFaderWindow().changeLable3("channel 11");
                Main.getFaderWindow().changeLable4("channel 12");
                break;
            case 4:
                Main.getFaderWindow().changeLable1("channel 13");
                Main.getFaderWindow().changeLable2("channel 14");
                Main.getFaderWindow().changeLable3("channel 15");
                Main.getFaderWindow().changeLable4("channel 16");
                break;
            case 5:
                Main.getFaderWindow().changeLable1("channel 17");
                Main.getFaderWindow().changeLable2("channel 18");
                Main.getFaderWindow().changeLable3("channel 19");
                Main.getFaderWindow().changeLable4("channel 20");
                break;
        }
    }
}
