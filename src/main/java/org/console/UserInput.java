package org.console;

import org.console.serial.SerialLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
    public static void userInput(){
        try {
            readConsol();
        }catch (Exception ignored){}
    }

    private static void readConsol() throws IOException, InterruptedException {
        System.out.println("[Channel starting with 0] [value]");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        //System.out.println(line);
        if(line.equals("restart")){
          System.out.println("restarting...");
          SerialLink.stop();
          SerialLink.run(Main.getSerialPort());
          return;
        }
        String[] split = line.split("\\ ");
        int channel = Integer.parseInt(split[0]);
        int value = Integer.parseInt(split[1]);
        if(channel > Main.getChannelCount()) return;
        if (value > 255) return;
        Main.getChannelList().get(channel).setValue(value);
        System.out.println("Channel : " + channel + " is set to : " + value);
    }
}
