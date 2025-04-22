package ui;

import models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JPanel {
    private final iScreenManager screenManager;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel errorLabel;
    private final ArrayList<String> usernames = new ArrayList<>();
    private final ArrayList<String> passwords = new ArrayList<>();

    public LoginPage(iScreenManager screenManager) {

        //TODO: initiate usernames and passwords at runtime from data.json or data.csv
        usernames.add("user1");
        passwords.add("pass1");

        this.screenManager = screenManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(20, 30, 70));

        // Title
        JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Book Antiqua", Font.BOLD, 60));
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(50));
        add(title);
        add(Box.createVerticalStrut(30));

        // Fields
        usernameField = new JTextField(16);
        passwordField = new JPasswordField(16);

        add(createField("Username:", usernameField));
        add(Box.createVerticalStrut(10));
        add(createField("Password:", passwordField));
        add(Box.createVerticalStrut(10));

        // Error Label
        errorLabel = new JLabel("Invalid username or password.");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(errorLabel);

        // Buttons
        add(Box.createVerticalStrut(30));
        add(createButton("Login", this::handleLogin));
        add(Box.createVerticalStrut(15));
        add(createButton("Create Account", this::handleCreate));
        add(Box.createVerticalStrut(50));

        add(createButton("TO homepage", this::handleHomepage));


    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(14, 42, 83));

        // ⭐️ This line centers the whole field+label stack
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(new EmptyBorder(0, 100, 0, 0)); // top, left, bottom, right

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(300, 30));
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(137, 207, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.addActionListener(action);
        return button;
    }

    private void handleHomepage(ActionEvent e) {
        screenManager.switchTo("Home Page");
    }

    private void handleLogin(ActionEvent e) {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        boolean found = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(user) && passwords.get(i).equals(pass)) {
                found = true;
                break;
            }
        }


        if (found) {
            errorLabel.setVisible(false);
            clearFields();

            // Create User and store it in MainFrame
            ((MainFrame) screenManager).setCurrentUser(new User(user, pass));

            screenManager.switchTo("Home Page");
        } else {
            errorLabel.setVisible(true);
        }
    }

    private void handleCreate(ActionEvent e) {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty() || usernames.contains(user)) {
            errorLabel.setText("Invalid or duplicate username.");
            errorLabel.setVisible(true);
            return;
        }

        usernames.add(user);
        passwords.add(pass);
        clearFields();
        errorLabel.setVisible(false);
        screenManager.switchTo("login"); // or go to homepage/prompt/etc.
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
