package nsu.oop.client.view.windows.authentication;

import javax.swing.*;
import java.awt.*;

public class Authentication extends JFrame {
    JLabel windowName, user, pass;
    JTextField nameField;
    JButton button;
    JPasswordField passField;

    public Authentication(String windowTitle, AuthenticationListener listener) {
        super(windowTitle + " Form");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        windowName = new JLabel(windowTitle + " Form");
        windowName.setForeground(Color.blue);
        windowName.setFont(new Font("Roboto", Font.BOLD, 20));
        user = new JLabel("Username");
        pass = new JLabel("Password");

        nameField = new JTextField("Kirill");
        passField = new JPasswordField("0000");
        button = new JButton(windowTitle);
        button.setPreferredSize(new Dimension(50, 150));
        switch (windowTitle) {
            case "Login" -> button.addActionListener(e -> listener.login());
            case "Registration" -> button.addActionListener(e -> listener.registration());
        }

        windowName.setBounds(50, 30, 400, 30);
        user.setBounds(50, 70, 200, 30);
        pass.setBounds(50, 110, 200, 30);
        nameField.setBounds(200, 70, 200, 30);
        passField.setBounds(200, 110, 200, 30);
        button.setBounds(50, 160, 150, 30);

        add(windowName);
        add(user);
        add(pass);
        add(nameField);
        add(passField);
        add(button);
        setVisible(true);
    }

    public String getPass() {
        return String.valueOf(passField.getPassword());
    }

    public String getUsername() {
        return nameField.getText();
    }
}
