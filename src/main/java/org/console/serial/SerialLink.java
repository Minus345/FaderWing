package org.console.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.console.Channel;
import org.console.Contend;
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
                        changeDmxSerialInput(build1);
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
                        Contend.setDisplayContend();
                    }
                    stringBuilder = new StringBuilder();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(build);
        }
    }

    private static void changeDmxSerialInput(String s) {
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
