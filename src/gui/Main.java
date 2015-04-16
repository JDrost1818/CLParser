package gui;

import simplestructures.SimpleFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    SimpleFrame topFrame;

    public static void main(String[] args) {
        new Main();
    }

    public Main () {
        UIManager.put("Label.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Button.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Label.foreground", new Color(0x404040));
        UIManager.put("TextField.font", new Font("Roboto Thin", 0, 18));
        UIManager.put("TextField.foreground", new Color(0x404040));

        topFrame = new SimpleFrame();

        JPanel contentPane = new JPanel(null);
        contentPane.setBounds(0, 50, topFrame.getWidth(), topFrame.getHeight()-50);

        contentPane.add(new Search(contentPane));

        topFrame.add(contentPane);
    }
}
