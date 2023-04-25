package org.console;

import java.io.*;
import java.util.Properties;

public class Contend {
    private static Properties prop;

    public static void LoadContendStart() {
        prop = new Properties();
        String fileName = "app.config";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not Found - Creating new one");
            createConfigFile();
        }
        try (
                FileInputStream fis = new FileInputStream(fileName)) {
                prop.load(fis);
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found");
            System.exit(1);
        } catch (
                IOException ignored) {
        }
        setDisplayContend();
    }

    public static void setDisplayContend() {
        switch (Main.getLayer()) {
            case 1 -> {
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.1"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.2"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.3"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.4"));
            }
            case 2 -> {
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.5"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.6"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.7"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.8"));
            }
            case 3 -> {
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.9"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.10"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.11"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.12"));
            }
            case 4 -> {
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.13"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.14"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.15"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.16"));
            }
            case 5 -> {
                Main.getFaderWindow().changeLable1(prop.getProperty("gui.channel.17"));
                Main.getFaderWindow().changeLable2(prop.getProperty("gui.channel.18"));
                Main.getFaderWindow().changeLable3(prop.getProperty("gui.channel.19"));
                Main.getFaderWindow().changeLable4(prop.getProperty("gui.channel.20"));
            }
        }
    }

    public static void createConfigFile() {
        try {
            File myObj = new File("app.config");
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter("app.config");
                PrintWriter printWriter = new PrintWriter(myWriter);
                printWriter.println("# Config");
                printWriter.println("gui.channel.1 = channel 1");
                printWriter.println("gui.channel.2 = channel 2");
                printWriter.println("gui.channel.3 = channel 3");
                printWriter.println("gui.channel.4 = channel 4");
                printWriter.println("gui.channel.5 = channel 5");
                printWriter.println("gui.channel.6 = channel 6");
                printWriter.println("gui.channel.7 = channel 7");
                printWriter.println("gui.channel.8 = channel 8");
                printWriter.println("gui.channel.9 = channel 9");
                printWriter.println("gui.channel.10 = channel 10");
                printWriter.println("gui.channel.11 = channel 11");
                printWriter.println("gui.channel.12 = channel 12");
                printWriter.println("gui.channel.13 = channel 13");
                printWriter.println("gui.channel.14 = channel 14");
                printWriter.println("gui.channel.15 = channel 15");
                printWriter.println("gui.channel.16 = channel 16");
                printWriter.println("gui.channel.17 = channel 17");
                printWriter.println("gui.channel.18 = channel 18");
                printWriter.println("gui.channel.19 = channel 19");
                printWriter.println("gui.channel.20 = channel 20");
                printWriter.close();
                System.out.println("Successfully wrote to the file.");
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
