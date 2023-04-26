package org.console;

import org.apache.commons.cli.*;
import org.console.artnet.SendArtNet;
import org.console.serial.SerialLink;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    private static final ArrayList<Channel> channelList = new ArrayList<>();
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

        //Read Command Line
        commandLineParameters(args);

        //Read Config
        Contend.LoadContendStart();
        if(!broadcast && unicastAddress == null){
            System.out.println("Please set Uni-cast or Broad-cast");
            System.exit(101);
        }

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
        System.out.println("Sending data to subnet: " + subnet + " universe: " + universe);
        SendArtNet.tick();

        //Starting Serial Com
        SerialLink.run(serialport);

        Thread.sleep(100);
/*
        while (true) {
            UserInput.userInput();
       }

 */
    }

    private static void commandLineParameters(String[] args) {
        var options = new Options()
                .addOption("b", "broadcast", false, "broadcast")
                .addOption(Option.builder("u")
                        .longOpt("uni-cast")
                        .desc("uni-cast ArtNet Destination IP")
                        .hasArg(true)
                        .build())
                .addOption(Option.builder("ip")
                        .longOpt("ipaddr")
                        .desc("ArtNet IP")
                        .hasArg(true)
                        .build())
                .addOption(Option.builder("s")
                        .longOpt("serialPort")
                        .desc("Serial Port (int)")
                        .hasArg(true)
                        .build())
                .addOption(Option.builder("su")
                        .longOpt("subnet")
                        .hasArg(true)
                        .build())
                .addOption(Option.builder("u")
                        .longOpt("subnet")
                        .hasArg(true)
                        .build());

        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        try {
            line = parser.parse(options, args);
            if (line.hasOption("b")) {
                broadcast = true;
                System.out.println("Using broadcast");
            }
            if (line.hasOption("u")) {
                unicastAddress = InetAddress.getByName(line.getOptionValue("u"));
                System.out.println("Using unicast to: " + unicastAddress.toString());
            }
            if (line.hasOption("ip")) {
                ipAddress = line.getOptionValue("ip");
            }
            if (line.hasOption("s")) {
                serialport = Integer.parseInt(line.getOptionValue("s"));
            }
            if (line.hasOption("su")) {
                subnet = Integer.parseInt(line.getOptionValue("su"));
            }
            if (line.hasOption("u")) {
                universe = Integer.parseInt(line.getOptionValue("u"));
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
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

    public static void setChangingLayer(boolean changingLayer) {
        Main.changingLayer = changingLayer;
    }

    public static FaderWindow getFaderWindow() {
        return faderWindow;
    }

}