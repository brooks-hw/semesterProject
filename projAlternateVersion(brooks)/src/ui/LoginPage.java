package ui;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginPage extends JPanel {
    private final iScreenManager screenManager;
    private final JTextField usernameField = new JTextField(16);
    private final JPasswordField passwordField = new JPasswordField(16);
    private final JLabel errorLabel = new JLabel();
    private final ArrayList<String> usernames;
    private final ArrayList<String> passwords;

    public LoginPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.usernames = new ArrayList<String>();
        this.passwords = new ArrayList<String>();

        usernames.add("user1");
        passwords.add("pass1");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(20, 30, 70));

        // Title
        JLabel title = new JLabel("Login / Create Account");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Book Antiqua", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(40));
        add(title);
        add(Box.createVerticalStrut(30));

        // Fields
        add(createField("Username:", usernameField));
        add(Box.createVerticalStrut(10));
        add(createField("Password:", passwordField));
        add(Box.createVerticalStrut(10));

        // Error label
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
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(14, 42, 83));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        field.setMaximumSize(new Dimension(300, 30));

        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(137, 207, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        boolean found = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(user) && passwords.get(i).equals(pass)) {
                found = true;
                break;
            }
        }

        //TODO: this temporary sets found to true to allow all logins for debugging
        found = true;

        if (found) {
            errorLabel.setVisible(false);
            clearFields();

            User newUser = new User(user, pass);
            ((MainFrame) screenManager).getHomePage().setup(newUser);
            screenManager.switchTo("PostLoginPrompt");
        } else {
            setError("Invalid username or password.");
        }
    }

    private void handleCreate() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty() || usernames.contains(user)) {
            setError("Invalid or duplicate username.");
            return;
        }

        usernames.add(user);
        passwords.add(pass);
        clearFields();

        User newUser = new User(user, pass);
        ((MainFrame) screenManager).getHomePage().setup(newUser);
        screenManager.switchTo("PostLoginPrompt");
    }

    private void setError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        errorLabel.setVisible(false);
    }
}
