package nsu.oop.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewUtils {
    public static JButton initButton(int width, int height, int posX, int posY, ActionListener listener) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.addActionListener(listener);
        Dimension startSize = button.getPreferredSize();
        button.setBounds(posX, posY, startSize.width, startSize.height);
        return button;
    }

    public static JLabel initLabel(String username, int fontSize, int width, int height, int posX, int posY) {
        JLabel name = new JLabel(username);
        name.setFont(new Font("Roboto", Font.BOLD, fontSize));
        name.setForeground(Color.WHITE);
        name.setPreferredSize(new Dimension(width, height));
        Dimension nameSize = name.getPreferredSize();
        name.setBounds(posX, posY, nameSize.width, nameSize.height);
        return name;
    }

    public static JScrollPane initScrollPane(int width, int height, int posX, int posY) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(width, height));
        Dimension scrollPaneSize = scrollPane.getPreferredSize();
        scrollPane.setBounds(posX, posY, scrollPaneSize.width, scrollPaneSize.height);
        return scrollPane;
    }
}
