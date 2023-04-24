package org.console.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.console.Channel;
import org.console.FaderWindow;
import org.console.Main;

import java.io.InputStream;

public class SerialLink {
    private static String build;
    private static Thread serialThread;
    private static SerialPort[] comPorts;
    private static boolean running;

    public static void run(int port) {
        running = true;
        Runnable runnable = () -> action(port);
        Thread thread = new Thread(runnable);
        thread.start();
        serialThread = thread;
    }

    private static void action(int port) {
        System.out.println("List COM ports");
        comPorts = SerialPort.getCommPorts();
        for (int i = 0; i < comPorts.length; i++)
            System.out.println("comPorts[" + i + "] = " + comPorts[i].getDescriptivePortName());
        comPorts[port].openPort();
        System.out.println("open port comPorts[" + port + "]  " + comPorts[port].getDescriptivePortName());
        comPorts[port].setBaudRate(9600);
        comPorts[port].setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPorts[port].getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        while (running) {
            try {
                stringBuilder.append((char) in.read());
                build = stringBuilder.toString();
                if (build.endsWith("\n")) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    String build1 = stringBuilder.toString();
                    try {
                        changeDmxSerialInput(build1, comPorts[port]);
                    } catch (Exception ignored) {
                    }
                    String layer = build1.substring(build1.length() - 1);
                    if (layer.equals("t") || layer.equals("f")) {
                        if (layer.equals("t")) {
                            Main.setChangingLayer(true);
                            Main.getFaderWindow().setPannelColour(true);
                            //System.out.println("Changing starting");
                        } else {
                            Main.setChangingLayer(false);
                            Main.getFaderWindow().setPannelColour(false);
                            //System.out.println("Changing stopp");
                        }
                    } else {
                        Main.setLayer(Integer.parseInt(layer));
                        Main.getFaderWindow().setLayer(layer);
                        setDisplay();
                    }
                    stringBuilder = new StringBuilder();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(build);
        }
    }

    public static void setDisplay() {
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

    private static void changeDmxSerialInput(String s, SerialPort port) {
        String[] split = s.split("\\|");
        //System.out.println(s);
        for (int i = 0; i < Main.getChannelCount(); i++) {
            Channel channel = Main.getChannelList().get(i);
            int value = Integer.parseInt(split[i]);
            channel.setValue(value);
        }
    }

    private static void writeBytesToArduino(SerialPort port) {
        StringBuilder stringBuilder = new StringBuilder();

        byte[] allAttributes = new byte[20];
        for (int i = 0; i < 20; i++) {
            stringBuilder.append(allAttributes[i]);
            stringBuilder.append("|");
        }

        build = stringBuilder.toString();
        byte[] buildBytes = build.getBytes();
        System.out.println("bytes: " + build);
        port.writeBytes(buildBytes, buildBytes.length);
    }

    public static void stop() {
        running = false;
        comPorts[Main.getSerialPort()].closePort();
        serialThread = null;
    }

}
