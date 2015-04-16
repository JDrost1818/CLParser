package gui;

import data.DATA;
import simplestructures.SimpleFrame;
import simplestructures.SimpleLabel;

import javax.swing.*;
import java.awt.*;

public class Search extends JPanel {

    CardLayout contentPanelLayout = new CardLayout();

    public static void main(String[] args) {
        SimpleFrame x = new SimpleFrame();
        JPanel contentPane = new JPanel(null);
        x.setContentPane(contentPane);

        Search app = new Search(contentPane);
        x.add(app);
        x.setVisible(true);
    }

    public Search(Container parent) {

        this.setSize(parent.getSize());

        // Title
        SimpleLabel title = new SimpleLabel("Create Search");
        title.setBounds(GUIData.STD_MARGIN, GUIData.STD_MARGIN/2, title.getWidth(), title.getHeight());
        title.setFont(title.getFont().deriveFont(GUIData.HEADER_FONT_SIZE));
        title.autoSize();
        add(title);

        // Content Panel
        JPanel contentPanel = new JPanel(contentPanelLayout);
        contentPanel.setBounds(
                GUIData.STD_MARGIN,
                GUIData.STD_MARGIN + title.getHeight(),
                parent.getWidth() - (2 * GUIData.STD_MARGIN),
                parent.getHeight() - (title.getHeight() + 3 * GUIData.STD_MARGIN));
        contentPanel.setBackground(DATA.COLORS.DARK_BLUE);
        add(contentPanel);

        // "Create Search" card
        JPanel createSearchPanel = new JPanel(null);
        createSearchPanel.setBounds(0 ,0, contentPanel.getWidth(), contentPanel.getHeight());
        createSearchPanel.setBackground(Color.WHITE);

        JTextField keywordEntry = new JTextField("SOMETHING");
        keywordEntry.setBounds(GUIData.STD_MARGIN, GUIData.STD_MARGIN, 100, 25);
        createSearchPanel.add(keywordEntry);
        contentPanel.add("create", createSearchPanel);

        // "Edit Search" card
        JPanel editSearchPanel = new JPanel(null);
        contentPanel.add("edit", editSearchPanel);

        contentPanelLayout.show(contentPanel, "create");

    }
}
