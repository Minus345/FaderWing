package org.console;

import org.console.artnet.SendArtNet;
import org.console.serial.SerialLink;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

public class Main {

    private static ArrayList<Channel> channelList = new ArrayList<>();
    private static int channelCount;
    private static int subnet;
    private static int universe;
    private static boolean broadcast;
    private static InetAddress unicastAddress;
    private static int serialport;
    private static boolean dmxJoker;

    private static int layer;
    private static boolean changingLayer;

    private static String ipAddress;
    private static FaderWindow faderWindow;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting!");
        //dmxJoker = true;

        ipAddress = args[4];

        //Read Config
        LoadContend.LoadContendStart();

        //creating GUI
        faderWindow = new FaderWindow();
        faderWindow.setTitle("Fader Wing");
        faderWindow.pack();
        faderWindow.setLocationRelativeTo(null);
        faderWindow.setVisible(true);

        //Creating Channels
        channelCount = 20;
        for (int i = 0; i < channelCount; i++) {
            Channel channelNew = new Channel(i, 0);
            channelList.add(channelNew);
            //System.out.println("Created Channel " + i);
        }
        channelList.sort(Comparator.comparingInt(Channel::getId));

        //Starting ArtNet
        System.out.println("Starting ArtNet");
        //InetAddress address = InetAddress.getByName(args[0]);
        if (args[1].equals("broadcast")) {
            broadcast = true;
            System.out.println("Using broadcast");
        } else {
            unicastAddress = InetAddress.getByName(args[1]);
            System.out.println("Using unicast to: " + unicastAddress.toString());
        }
        subnet = Integer.parseInt(args[2]);
        universe = Integer.parseInt(args[3]);
        System.out.println("Sending data to subnet: " + subnet + " universe: " + universe);
        SendArtNet.tick(); //192.168.178.131

        //Starting Serial Com
        serialport = Integer.parseInt(args[0]);
        SerialLink.run(serialport);

        Thread.sleep(100);

        while (true) {
            UserInput.userInput();
        }
    }

    public static ArrayList<Channel> getChannelList() {
        return channelList;
    }

    public static int getChannelCount() {
        return channelCount;
    }

    public static int getSubnet() {
        return subnet;
    }

    public static int getUniverse() {
        return universe;
    }

    public static boolean isBroadcast() {
        return broadcast;
    }

    public static InetAddress getUnicastAddress() {
        return unicastAddress;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static int getSerialPort() {
        return serialport;
    }

    public static boolean isDmxJoker() {
        return dmxJoker;
    }

    public static void setDmxJoker(boolean dmxJoker) {
        Main.dmxJoker = dmxJoker;
    }

    public static int getLayer() {
        return layer;
    }

    public static void setLayer(int layer) {
        Main.layer = layer;
    }

    public static boolean isChangingLayer() {
        return changingLayer;
    }

    public static void setChangingLayer(boolean changingLayer) {
        Main.changingLayer = changingLayer;
    }

    public static FaderWindow getFaderWindow() {
        return faderWindow;
    }

    public static void setFaderWindow(FaderWindow faderWindow) {
        Main.faderWindow = faderWindow;
    }
}