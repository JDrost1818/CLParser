package gui;

import data.DATA;
import data.constants.SimpleConstants;
import main.Manager;
import objects.Search;
import simplestructures.SimpleFrame;
import simplestructures.SimpleSeparator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    private SimpleFrame topFrame;
    private Manager manager;
    private CardLayout contentLayout = new CardLayout();

    // Cards
    private JPanel contentPane = new JPanel(contentLayout);
    private SearchGUI searchGUI;

    public static void main(String[] args) {
        new Main(new Manager());
    }

    public Main (Manager _manager) {
        UIManager.put("Label.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Button.font", new Font("Roboto Light", 0, 18));
        UIManager.put("Label.foreground", new Color(0x404040));
        UIManager.put("TextField.font", new Font("Roboto", 0, 18));
        UIManager.put("TextField.foreground", DATA.COLORS.BLACK);

        manager = _manager;

        // This is the GUI that will show up
        // first. It gathers the email credentials
        // for the user so that they can email
        // themselves the post.
        new LoginGUI(Main.this);

        // This is the main application GUI.
        // It is initially hidden while the
        // login screen is visible
        topFrame = new SimpleFrame();

        contentPane.setFocusable(true);
        contentPane.setBounds(0, 25, topFrame.getWidth(), topFrame.getHeight()-25);
        contentPane.setBackground(Color.WHITE);

        searchGUI = new SearchGUI(Main.this, contentPane);

        contentPane.add("search", searchGUI);
        topFrame.add(contentPane);
        topFrame.setVisible(false);
    }

    /*
        Currently just assumes that the user is competent
        and will enter their gmail information in correctly
     */
    public boolean login(final String username, final String password) {
        manager.login(username, password);
        topFrame.setVisible(true);
        return true;
    }

    public void addSearch(Search newSearch) {
        manager.addSearch(newSearch);
    }

    public void search(final Search customSearch) {
        manager.singleSearch(new ArrayList<Search>() {{
            add(customSearch);
        }});
    }
}
