package org.console;

import org.console.artnet.SendArtNet;
import org.console.serial.SerialLink;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    private static ArrayList<Channel> channelList = new ArrayList<>();

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Starting!");
        
        //Creating Channels
        for (int i = 0; i < 20; i++){
            Channel channelNew = new  Channel(i,0);
            channelList.add(channelNew);
            System.out.println("Created Channel " + i);
        }

        channelList.sort(Comparator.comparingInt(Channel::getId));

        //Starting ArtNet
        InetAddress address = InetAddress.getByName(args[0]);
        SendArtNet.tick(address); //192.168.178.131

        //Starting Serial Com
        int port = Integer.parseInt(args[1]);
        SerialLink.run(port);
    }

    public static ArrayList<Channel> getChannelList() {
        return channelList;
    }
}