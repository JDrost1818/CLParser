package gui;

import data.CraigslistUrls;
import data.DATA;
import objects.Search;
import simplestructures.SimpleButton;
import simplestructures.SimpleLabel;
import simplestructures.SimpleTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchGUI extends JPanel {

    private CardLayout contentPanelLayout = new CardLayout();

    private Main controller;

    private String keywordHoldingText = "Keywords";
    private String excludeWordHoldingText = "Excluded Words";
    private JPanel contentPanel;

    public SearchGUI(Main _controller, Container parent) {
        this.controller = _controller;
        this.setSize(parent.getSize());

        // Title
        SimpleLabel title = new SimpleLabel("Create Search");
        title.setBounds(GUIData.STD_MARGIN, GUIData.STD_MARGIN/2, title.getWidth(), title.getHeight());
        title.setFont(title.getFont().deriveFont(GUIData.HEADER_FONT_SIZE));
        title.autoSize();
        add(title);

        // Content Panel
        contentPanel = new JPanel(contentPanelLayout);
        contentPanel.setBounds(
                GUIData.STD_MARGIN,
                GUIData.STD_MARGIN + title.getHeight(),
                parent.getWidth() - (2 * GUIData.STD_MARGIN),
                parent.getHeight() - (title.getHeight() + 3 * GUIData.STD_MARGIN));
        contentPanel.setBackground(DATA.COLORS.DARK_BLUE);
        add(contentPanel);

        // "Create Search" card
        contentPanel.add("create", buildCreateCard(contentPanel));

        // "Edit Search" card
        JPanel editSearchPanel = new JPanel(null);
        contentPanel.add("edit", editSearchPanel);

        display("create");

    }

    public JPanel buildCreateCard(Container parent) {
        JPanel createSearchPanel = new JPanel(null);
        createSearchPanel.setBounds(0 ,0, parent.getWidth(), parent.getHeight());
        createSearchPanel.setBackground(Color.WHITE);

        // Holds text telling user what the keyword text field is for
        SimpleLabel keywordLabel = new SimpleLabel("Enter Keywords: ");
        keywordLabel.setBounds(GUIData.STD_MARGIN, 2 * GUIData.STD_MARGIN,
                keywordLabel.getWidth(), keywordLabel.getHeight());
        createSearchPanel.add(keywordLabel);

        // Holds text telling user what the exclude text field is for
        SimpleLabel excludeLabel = new SimpleLabel("Words to Exclude: ");
        excludeLabel.setBounds(keywordLabel.getX(), keywordLabel.getY() + keywordLabel.getHeight() + (int) (.75 * GUIData.STD_MARGIN),
                excludeLabel.getWidth(), keywordLabel.getHeight());
        createSearchPanel.add(excludeLabel);

        // Textfield where user can enter words to ignore during search
        final SimpleTextField excludedWordsEntry = new SimpleTextField(excludeWordHoldingText);
        excludedWordsEntry.setBounds(
                GUIData.STD_MARGIN + excludeLabel.getWidth(),
                excludeLabel.getY(),
                createSearchPanel.getWidth() - (2 * GUIData.STD_MARGIN + excludeLabel.getWidth()),
                (int) GUIData.HEADER_FONT_SIZE + 10);
        createSearchPanel.add(excludedWordsEntry);

        // Textfield where user can enter words to look for during search
        final SimpleTextField keywordEntry = new SimpleTextField(keywordHoldingText);
        keywordEntry.setBounds(
                excludedWordsEntry.getX(),
                keywordLabel.getY(),
                excludedWordsEntry.getWidth(),
                (int) GUIData.HEADER_FONT_SIZE + 10);
        createSearchPanel.add(keywordEntry);

        // Holds text telling user what the min text field is for
        SimpleLabel minPriceLabel = new SimpleLabel("Min Price: ");
        minPriceLabel.setBounds(
                GUIData.STD_MARGIN,
                excludeLabel.getY() + excludeLabel.getHeight() + (int) (.9 * GUIData.STD_MARGIN),
                minPriceLabel.getWidth(), minPriceLabel.getHeight());
        createSearchPanel.add(minPriceLabel);

        // Textfield where user can enter words to look for during search
        final SimpleTextField minPriceEntry = new SimpleTextField("");
        minPriceEntry.setBounds(
                minPriceLabel.getX() + minPriceLabel.getWidth() + (int)(.25 * GUIData.STD_MARGIN),
                minPriceLabel.getY(),
                75,
                minPriceLabel.getHeight());
        createSearchPanel.add(minPriceEntry);

        // Holds text telling user what the max text field is for
        SimpleLabel maxPriceLabel = new SimpleLabel("Max Price: ");
        maxPriceLabel.setBounds(
                minPriceEntry.getX() + minPriceEntry.getWidth() + GUIData.STD_MARGIN,
                minPriceEntry.getY(),
                maxPriceLabel.getWidth(),
                maxPriceLabel.getHeight());
        createSearchPanel.add(maxPriceLabel);

        // Textfield where user can enter words to look for during search
        final SimpleTextField maxPriceEntry = new SimpleTextField("");
        maxPriceEntry.setBounds(
                maxPriceLabel.getX() + maxPriceLabel.getWidth() + (int)(.25 * GUIData.STD_MARGIN),
                maxPriceLabel.getY(),
                75,
                maxPriceLabel.getHeight());
        createSearchPanel.add(maxPriceEntry);

        // Takes all input and creates the search object and searches on it
        SimpleButton createButton = new SimpleButton("Search", DATA.COLORS.LIGHT_BLUE, true);
        createButton.setActiveForeground(Color.white);
        createButton.setBounds(
                (createSearchPanel.getWidth() / 2) - (GUIData.WIDE_BUTTON_W / 2),
                createSearchPanel.getHeight() - (GUIData.WIDE_BUTTON_H + GUIData.STD_MARGIN),
                GUIData.WIDE_BUTTON_W,
                GUIData.WIDE_BUTTON_H);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Gets and creates the search string
                String search = keywordEntry.getText() + " ";
                String[] exclusions = excludedWordsEntry.getText().split(" ");
                for (String exclusion : exclusions) {
                    search += "-" + exclusion + " ";
                }

                // gets the price
                int minPrice = -1, maxPrice = -1;
                if (!minPriceEntry.getText().equals("")) {
                    try {
                        minPrice = Integer.parseInt(minPriceEntry.getText());
                    } catch (NumberFormatException nfe) {
                        // @TODO handle this by notifying user
                    }
                }
                if (!maxPriceEntry.getText().equals("")) {
                    try {
                        maxPrice = Integer.parseInt(maxPriceEntry.getText());
                    } catch (NumberFormatException nfe) {
                        // @TODO handle this by notifying user
                    }
                }

                // Creates search, saves if requested, and runs the search
                Search newSearch = new Search(CraigslistUrls.ALL.owner(), "Minneapolis", search, minPrice, maxPrice);
                int reply = JOptionPane.showConfirmDialog(null, "Would you like to save this\nsearch for later use?", "", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                    controller.addSearch(newSearch);
                controller.search(newSearch);
            }
        });
        createSearchPanel.add(createButton);

        return createSearchPanel;
    }

    public void display(String key) {
        contentPanelLayout.show(contentPanel, key);
    }
}
