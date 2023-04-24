package org.console;

import javax.swing.*;
import java.awt.*;

public class FaderWindow extends JFrame {
    private JPanel contantPane;
    private JLabel Lable1;
    private JLabel Label2;
    private JLabel Lable3;
    private JLabel Lable4;
    private JLabel Layer;
    private JPanel Pannel;
    private JTabbedPane tabbedPane1;
    private JCheckBox jokerCheckBox;

    public FaderWindow() {
        add(contantPane);
        Pannel.setBackground(Color.GREEN);
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
