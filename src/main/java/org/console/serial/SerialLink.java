package org.console.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.console.Channel;
import org.console.Main;

import java.io.InputStream;

public class SerialLink {
    private static String build;

    public static void run(int port) {
        Runnable runnable = () -> action(port);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void action(int port) {
        System.out.println("List COM ports");
        SerialPort[] comPorts = SerialPort.getCommPorts();
        for (int i = 0; i < comPorts.length; i++)
            System.out.println("comPorts[" + i + "] = " + comPorts[i].getDescriptivePortName());
        comPorts[port].openPort();
        System.out.println("open port comPorts[" + port + "]  " + comPorts[port].getDescriptivePortName());
        comPorts[port].setBaudRate(9600);
        comPorts[port].setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPorts[port].getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
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

                    stringBuilder = new StringBuilder();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(build);
        }
    }

    private static void changeDmxSerialInput(String s, SerialPort port) {
        String[] split = s.split("\\|");
        for (int i = 0; i < split.length; i++) {
            Channel channel = Main.getChannelList().get(i);
            channel.setValue(Integer.parseInt(split[i]));
        }
        //System.out.println(s);
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
}
