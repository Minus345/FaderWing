package org.console.artnet;

import ch.bildspur.artnet.ArtNetClient;
import org.console.Main;

import java.net.InetAddress;
import java.util.Arrays;

public class SendArtNet {

    private static final byte[] dmxData = new byte[512];
    private static ArtNetClient artNetClient;

    public static void createArtNetController(InetAddress address) {
        artNetClient = new ArtNetClient();
        artNetClient.start(address);
    }

    public static void sendData() {
        for (int i = 0; i < Main.getChannelList().size(); i++){
            dmxData[i] = (byte) Main.getChannelList().get(i).getValue();
        }
        sendArtNetData(dmxData);
        Arrays.fill(dmxData, (byte) 0);
    }

    private static void sendArtNetData(byte[] data) {
        artNetClient.broadcastDmx(0, 1, data);
    }

    public static void tick(InetAddress address) {
        createArtNetController(address);
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
