package gui;

import data.CraigslistUrls;
import data.DATA;
import objects.Search;
import simplestructures.SimpleButton;
import simplestructures.SimpleLabel;
import simplestructures.SimpleTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchGUI extends JPanel implements iCompressible {

    private Main controller;

    private CardLayout contentPanelLayout = new CardLayout();

    private String DEFAULT_KEYWORDS_HOLD = "Keywords";
    private String DEFAULT_EXCLUDE_HOLD = "Excluded Words";
    private String DEFAULT_MIN_PRICE_HOLD = "";
    private String DEFAULT_MAX_PRICE_HOLD = "";
    private String DEFAULT_CREATE_TITLE = "Create Search";
    private String DEFAULT_EDIT_TITLE = "Edit Search";
    private String BUTTON_CREATE_TITLE = "Search";
    private String BUTTON_EDIT_TITLE = "Edit";


    private JPanel contentPanel;

    private SimpleLabel title;
    private SimpleTextField keywordEntry;
    private SimpleTextField excludedWordsEntry;
    private SimpleTextField minPriceEntry;
    private SimpleTextField maxPriceEntry;
    private SimpleButton button;

    private SimpleLabel keywordLabel;
    private SimpleLabel excludeLabel;
    private SimpleLabel minPriceLabel;
    private SimpleLabel maxPriceLabel;

    private JPanel createSearchPanel;

    public SearchGUI(Main _controller, Container parent) {
        this.controller = _controller;
        this.setSize(parent.getSize());

        setBackground(parent.getBackground());
        setLayout(null);

        // Title
        title = new SimpleLabel(DEFAULT_CREATE_TITLE);
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
                parent.getHeight() - (title.getHeight() + 2 * GUIData.STD_MARGIN));
        contentPanel.setBackground(parent.getBackground());
        add(contentPanel);

        // "Create Search" card
        contentPanel.add("create", buildCreateCard(contentPanel));

        setToCreate();
    }

    public JPanel buildCreateCard(Container parent) {
        createSearchPanel = new JPanel(null);
        createSearchPanel.setBounds(0 ,0, parent.getWidth(), parent.getHeight());
        System.out.println(createSearchPanel.getX() + " " + createSearchPanel.getWidth());
        createSearchPanel.setBorder(new LineBorder(DATA.COLORS.BORDER_COLOR));
        createSearchPanel.setBackground(Color.WHITE);

        // Holds text telling user what the keyword text field is for
        keywordLabel = new SimpleLabel("Enter Keywords: ");
        createSearchPanel.add(keywordLabel);

        // Holds text telling user what the exclude text field is for
        excludeLabel = new SimpleLabel("Words to Exclude: ");
        createSearchPanel.add(excludeLabel);

        // Textfield where user can enter words to ignore during search
        excludedWordsEntry = new SimpleTextField(DEFAULT_EXCLUDE_HOLD);
        createSearchPanel.add(excludedWordsEntry);

        // Textfield where user can enter words to look for during search
        keywordEntry = new SimpleTextField(DEFAULT_KEYWORDS_HOLD);
        createSearchPanel.add(keywordEntry);

        // Holds text telling user what the min text field is for
        minPriceLabel = new SimpleLabel("Min Price: ");
        createSearchPanel.add(minPriceLabel);

        // Textfield where user can enter words to look for during search
        minPriceEntry = new SimpleTextField(DEFAULT_MIN_PRICE_HOLD);
        createSearchPanel.add(minPriceEntry);

        // Holds text telling user what the max text field is for
        maxPriceLabel = new SimpleLabel("Max Price: ");
        createSearchPanel.add(maxPriceLabel);

        // Textfield where user can enter words to look for during search
        maxPriceEntry = new SimpleTextField(DEFAULT_MAX_PRICE_HOLD);
        createSearchPanel.add(maxPriceEntry);

        // Takes all input and creates the search object and searches on it
        button = new SimpleButton("hello World", DATA.COLORS.LIGHT_BLUE, true);
        button.setActiveForeground(Color.white);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Gets and creates the search string
                if (button.getText().equals("Edit")) {
                    // this will do something else
                } else {
                    // Sets to what is found at the entry points; however, if what
                    // is found is the default (ie the user did not enter anything),
                    // sets to the empty string
                    String search = keywordEntry.getText().equals(DEFAULT_KEYWORDS_HOLD) ? "" : keywordEntry.getText();
                    String exclusion = excludedWordsEntry.getText().equals(DEFAULT_EXCLUDE_HOLD) ? "" : excludedWordsEntry.getText();

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
                    Search newSearch = new Search(CraigslistUrls.ALL.owner(), "Minneapolis", search, exclusion, minPrice, maxPrice);
                    int reply = JOptionPane.showConfirmDialog(null, "Would you like to save this\nsearch for later use?", "", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION)
                        controller.addSearch(newSearch);
                    controller.search(newSearch);
                }
            }
        });
        createSearchPanel.add(button);
        updateComponentLocations();

        return createSearchPanel;
    }

    public void setToCreate() {
        title.setText(DEFAULT_CREATE_TITLE);
        keywordEntry.setText(DEFAULT_KEYWORDS_HOLD);
        excludedWordsEntry.setText(DEFAULT_EXCLUDE_HOLD);
        minPriceEntry.setText(DEFAULT_MIN_PRICE_HOLD);
        maxPriceEntry.setText(DEFAULT_MAX_PRICE_HOLD);
        button.setText(BUTTON_CREATE_TITLE);

        contentPanelLayout.show(contentPanel, "create");
    }

    public void setToEdit(Search search) {
        title.setText(DEFAULT_EDIT_TITLE);
        keywordEntry.setText(search.match());
        excludedWordsEntry.setText(search.exclusions());
        minPriceEntry.setText((search.minPrice() == -1) ? "" : Integer.toString(search.minPrice()));
        maxPriceEntry.setText((search.maxPrice() == -1) ? "" : Integer.toString(search.maxPrice()));
        button.setText(BUTTON_EDIT_TITLE);

        contentPanelLayout.show(contentPanel, "create");
    }

    @Override
    public void shrink(int numPixels, int stopPosition) {
        createSearchPanel.setBounds(
                createSearchPanel.getX(),
                createSearchPanel.getY(),
                createSearchPanel.getWidth() - numPixels,
                createSearchPanel.getHeight());
        updateComponentLocations();
    }

    @Override
    public void expand(int numPixels, int stopPosition) {
        createSearchPanel.setBounds(
                createSearchPanel.getX(),
                createSearchPanel.getY(),
                createSearchPanel.getWidth() + numPixels,
                createSearchPanel.getHeight());
        updateComponentLocations();
    }

    public void updateComponentLocations() {
        keywordLabel.setBounds(
                GUIData.STD_MARGIN,
                GUIData.STD_MARGIN * 2,
                keywordLabel.getWidth(),
                keywordLabel.getHeight());

        excludeLabel.setBounds(
                keywordLabel.getX(),
                keywordLabel.getY() + keywordLabel.getHeight() + (int) (.75 * GUIData.STD_MARGIN),
                excludeLabel.getWidth(),
                keywordLabel.getHeight());

        excludedWordsEntry.setBounds(
                GUIData.STD_MARGIN + excludeLabel.getWidth(),
                excludeLabel.getY(),
                createSearchPanel.getWidth() - (2 * GUIData.STD_MARGIN + excludeLabel.getWidth()),
                (int) GUIData.HEADER_FONT_SIZE + 10);

        keywordEntry.setBounds(
                excludedWordsEntry.getX(),
                keywordLabel.getY(),
                excludedWordsEntry.getWidth(),
                (int) GUIData.HEADER_FONT_SIZE + 10);

        minPriceLabel.setBounds(
                GUIData.STD_MARGIN,
                excludeLabel.getY() + excludeLabel.getHeight() + (int) (.9 * GUIData.STD_MARGIN),
                minPriceLabel.getWidth(),
                minPriceLabel.getHeight());

        minPriceEntry.setBounds(
                minPriceLabel.getX() + minPriceLabel.getWidth() + (int)(.25 * GUIData.STD_MARGIN),
                minPriceLabel.getY(),
                75,
                minPriceLabel.getHeight());

        maxPriceLabel.setBounds(
                minPriceEntry.getX() + minPriceEntry.getWidth() + GUIData.STD_MARGIN,
                minPriceEntry.getY(),
                maxPriceLabel.getWidth(),
                maxPriceLabel.getHeight());

        maxPriceEntry.setBounds(
                maxPriceLabel.getX() + maxPriceLabel.getWidth() + (int)(.25 * GUIData.STD_MARGIN),
                maxPriceLabel.getY(),
                75,
                maxPriceLabel.getHeight());

        button.setBounds(
                (createSearchPanel.getWidth() / 2) - (GUIData.WIDE_BUTTON_W / 2),
                createSearchPanel.getHeight() - (GUIData.WIDE_BUTTON_H + GUIData.STD_MARGIN),
                GUIData.WIDE_BUTTON_W,
                GUIData.WIDE_BUTTON_H);
    }
}
