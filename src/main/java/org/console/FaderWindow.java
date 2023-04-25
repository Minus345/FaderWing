package org.console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class FaderWindow extends JFrame {
    private JPanel contantPane;
    private JLabel Lable1;
    private JLabel Label2;
    private JLabel Lable3;
    private JLabel Lable4;
    private JLabel Layer;
    private JPanel Pannel;
    private JCheckBox jokerCheckBox;

    public FaderWindow() {
        add(contantPane);
        Pannel.setBackground(Color.GREEN);
        Main.setDmxJoker(false);
        jokerCheckBox.addActionListener(e -> System.out.println(e.getID() == ActionEvent.ACTION_PERFORMED ? "ACTION_PERFORMED" : e.getID()));
        jokerCheckBox.addItemListener(e -> {
            System.out.println(e.getStateChange() == ItemEvent.SELECTED ? "SELECTED" : "DESELECTED");
            int a = e.getStateChange();
            if (a == 1) {
                Main.setDmxJoker(true);
            } else {
                Main.setDmxJoker(false);
            }
            System.out.println(a);
        });

    }

    public void setPannelColour(boolean red) {
        if (red) {
            Pannel.setBackground(Color.RED);
        } else {
            Pannel.setBackground(Color.GREEN);
        }
    }

    public void setLayer(String text) {
        Layer.setText(text);
    }

    public void changeLable1(String text) {
        Lable1.setText(text);
    }

    public void changeLable2(String text) {
        Label2.setText(text);
    }

    public void changeLable3(String text) {
        Lable3.setText(text);
    }

    public void changeLable4(String text) {
        Lable4.setText(text);
    }

}
