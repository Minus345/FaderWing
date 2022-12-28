package org.console;

import org.console.artnet.SendArtNet;
import org.console.serial.SerialLink;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    private static ArrayList<Channel> channelList = new ArrayList<>();
    private static int channelCount;
    private static int subnet;
    private static int universe;
    private static boolean broadcast;
    private static InetAddress unicastAddress;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting!");

        //Creating Channels
        channelCount = 4;
        for (int i = 0; i < channelCount; i++){
            Channel channelNew = new  Channel(i,0);
            channelList.add(channelNew);
            System.out.println("Created Channel " + i);
        }

        channelList.sort(Comparator.comparingInt(Channel::getId));

        //Starting ArtNet
        System.out.println("Starting ArtNet");
        //InetAddress address = InetAddress.getByName(args[0]);
        if(args[1].equals("broadcast")){
            broadcast = true;
            System.out.println("Using broadcast");
        }else{
            unicastAddress = InetAddress.getByName(args[1]);
            System.out.println("Using unicast to: " + unicastAddress.toString());
        }
        subnet = Integer.parseInt(args[2]);
        universe = Integer.parseInt(args[3]);
        System.out.println("Sending data to subnet: " + subnet + " universe: " + universe);
        SendArtNet.tick(); //192.168.178.131

        //Starting Serial Com
        int port = Integer.parseInt(args[0]);
        SerialLink.run(port);

        Thread.sleep(100);

        while (true){
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
}