package org.console.artnet;

import ch.bildspur.artnet.ArtNetClient;
import org.console.Main;

import java.util.Arrays;

public class SendArtNet {

    private static final byte[] dmxData = new byte[512];
    private static ArtNetClient artNetClient;

    public static void createArtNetController() {
        artNetClient = new ArtNetClient();
        artNetClient.start(Main.getIpAddress());//192.168.178.190
    }

    public static void sendData() {
        for (int i = 0; i < Main.getChannelList().size(); i++){
            dmxData[i] = (byte) Main.getChannelList().get(i).getValue();
        }
        //dmxData[0] = 111;
        sendArtNetData(dmxData);
        Arrays.fill(dmxData, (byte) 0);
    }

    private static void sendArtNetData(byte[] data) {
        if(Main.isBroadcast()){
            artNetClient.broadcastDmx(Main.getSubnet(), Main.getUniverse(), data);
        }else {
            artNetClient.unicastDmx(Main.getUnicastAddress(), Main.getSubnet(), Main.getUniverse(), data);
        }
    }

    public static void tick() {
        createArtNetController();
        Runnable runnable = () -> {
            while (Thread.currentThread().isAlive()) {
                sendData();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
