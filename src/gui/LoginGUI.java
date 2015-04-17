package gui;

import data.DATA;
import simplestructures.*;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGUI {

    private Main controller;

    private final String USERNAME_TEXT = "Email Address";
    private final String PASSWORD_TEXT = "Password";

    private ImageIcon logo = new ImageIcon(getClass().getResource("/resources/images/CraigslistLogo.png"));

    public LoginGUI(Main _controller) {
        this.controller = _controller;

        final SimpleFrame loginFrame = new SimpleFrame(new Dimension(350, 450));
        loginFrame.setLayout(null);


        int imageWidth = 134;
        int imageHeight = 110;

        // Image. This will be some holder image,
        // don't know what yet.
        JLabel image = new JLabel();
        image.setIcon(logo);
        image.setBounds(loginFrame.getWidth()/2 - imageWidth/2, 65, imageWidth, imageHeight);
        loginFrame.add(image);

        // Email Stuff
        SimpleLabel emailLabel = new SimpleLabel("Email: ");
        emailLabel.setBounds(
                GUIData.STD_MARGIN,
                image.getY() + image.getHeight() + (int) (.5 * GUIData.STD_MARGIN),
                emailLabel.getWidth(),
                emailLabel.getHeight());
        loginFrame.add(emailLabel);

        final SimpleTextField emailEntry = new SimpleTextField(USERNAME_TEXT);
        emailEntry.setBounds(
                GUIData.STD_MARGIN,
                emailLabel.getY() + emailLabel.getHeight() + (int) (.1 * GUIData.STD_MARGIN),
                loginFrame.getWidth() - 2 * GUIData.STD_MARGIN,
                emailLabel.getHeight());
        loginFrame.add(emailEntry);

        // Password Stuff
        SimpleLabel passwordLabel = new SimpleLabel("Password: ");
        passwordLabel.setBounds(
                GUIData.STD_MARGIN,
                emailEntry.getY() + emailEntry.getHeight() + (int) (.25 * GUIData.STD_MARGIN),
                passwordLabel.getWidth(),
                passwordLabel.getHeight());
        loginFrame.add(passwordLabel);

        final SimplePasswordField passwordEntry = new SimplePasswordField(PASSWORD_TEXT);
        passwordEntry.setBounds(
                GUIData.STD_MARGIN,
                passwordLabel.getY() + passwordLabel.getHeight() + (int) (.1 * GUIData.STD_MARGIN),
                loginFrame.getWidth() - 2 * GUIData.STD_MARGIN,
                passwordLabel.getHeight());
        loginFrame.add(passwordEntry);

        // Login button
        SimpleButton loginButton = new SimpleButton("Login", DATA.COLORS.GREEN, DATA.COLORS.DARK_GRAY, Color.white, true);
        loginButton.setSize(new Dimension(GUIData.WIDE_BUTTON_W, GUIData.WIDE_BUTTON_H));
        loginButton.setBounds(
                SimpleTools.centerH(0, loginFrame.getWidth(), loginButton),
                SimpleTools.centerV(passwordEntry, loginFrame.getHeight(), loginButton),
                loginButton.getWidth(),
                loginButton.getHeight());
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!controller.login(emailEntry.getText(), new String(passwordEntry.getPassword()))) {
                    System.out.println("WRONG USERNAME/PASSWORD");
                } else {
                    loginFrame.dispose();
                }
            }
        });
        loginFrame.add(loginButton);
    }
}
