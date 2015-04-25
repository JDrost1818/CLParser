package gui;

import data.DATA;
import main.Manager;
import objects.Search;
import simplestructures.SimpleFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

    private SimpleFrame topFrame;
    private Manager manager;
    private CardLayout contentLayout = new CardLayout();

    // Cards
    private JPanel contentPane = new JPanel(contentLayout);
    private JPanel sidePanel;
    private SearchGUI searchGUI;

    // Info
    private int sideMenuWidth;
    private int sideMenuStartPos;

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
        sideMenuWidth = 200;
        sideMenuStartPos = 0;

        // This is the GUI that will show up
        // first. It gathers the email credentials
        // for the user so that they can email
        // themselves the post.
        new LoginGUI(Main.this);

        // This is the main application GUI.
        // It is initially hidden while the
        // login screen is visible
        topFrame = new SimpleFrame();

        sidePanel = new JPanel(null);
        sidePanel.setBounds(sideMenuStartPos,0,sideMenuWidth,topFrame.getHeight());
        sidePanel.setBackground(Color.black);
        topFrame.add(sidePanel);

        contentPane.setFocusable(true);
        contentPane.setBounds(
                (sideMenuWidth + sideMenuStartPos),
                25,
                topFrame.getWidth() - (sideMenuWidth + sideMenuStartPos),
                topFrame.getHeight()-25);
        contentPane.setBackground(Color.WHITE);

        searchGUI = new SearchGUI(contentPane);

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
        hide();
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

    public void expand() {
        new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sidePanel.getX() >= 0) {
                    sidePanel.setBounds(0, sidePanel.getY(), sidePanel.getWidth(), sidePanel.getHeight());
                    ((Timer) e.getSource()).stop();
                } else {
                    sidePanel.setBounds(sidePanel.getX()+3, sidePanel.getY(), sidePanel.getWidth(), sidePanel.getHeight());
                    if (contentPane.getX() + GUIData.STD_MARGIN < sidePanel.getWidth() - GUIData.STD_MARGIN) {
                        contentPane.setBounds(contentPane.getX() + 4, contentPane.getY(), contentPane.getWidth() - 4, contentPane.getHeight());
                    } else if (contentPane.getX() + GUIData.STD_MARGIN != sideMenuWidth) {
                        int change = sideMenuWidth - contentPane.getX() - GUIData.STD_MARGIN;
                        contentPane.setBounds(contentPane.getX() + change, contentPane.getY(), contentPane.getWidth() - change, contentPane.getHeight());
                    }
                }
            }
        }).start();
    }

    public void hide() {
        new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contentPane.getX() <= 0) {
                    contentPane.setBounds(
                            0,
                            25,
                            topFrame.getWidth(),
                            topFrame.getHeight()-25);
                    ((Timer) e.getSource()).stop();
                } else {
                    sidePanel.setBounds(-sideMenuWidth, sidePanel.getY(), sidePanel.getWidth(), sidePanel.getHeight());
                    contentPane.setBounds(contentPane.getX() - 4, contentPane.getY(), contentPane.getWidth() + 4, contentPane.getHeight());
                }
            }
        }).start();
    }
}
