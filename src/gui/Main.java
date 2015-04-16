package gui;

import main.Manager;
import objects.Search;
import simplestructures.SimpleFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    private SimpleFrame topFrame;
    private Manager manager;

    public static void main(String[] args) {
        new Main(new Manager());
    }

    public Main (Manager _manager) {
        UIManager.put("Label.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Button.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Label.foreground", new Color(0x404040));
        UIManager.put("TextField.font", new Font("Roboto Thin", 0, 18));
        UIManager.put("TextField.foreground", new Color(0x404040));

        topFrame = new SimpleFrame();
        manager = _manager;

        JPanel contentPane = new JPanel(null);
        contentPane.setFocusable(true);
        contentPane.setBounds(0, 50, topFrame.getWidth(), topFrame.getHeight()-50);

        contentPane.add(new SearchGUI(this, contentPane));

        topFrame.add(contentPane);
    }

    public void addSearch(Search newSearch) {
        manager.addSearch(newSearch);
    }

    public void search(final Search customSearch) {
        manager.parsePages(new ArrayList<Search>() {{ add(customSearch); }});
    }
}
