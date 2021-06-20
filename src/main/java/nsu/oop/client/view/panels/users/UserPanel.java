package nsu.oop.client.view.panels.users;

import nsu.oop.client.view.ViewUtils;
import nsu.oop.client.view.panels.WindowPanel;

import javax.swing.*;

public class UserPanel extends WindowPanel {
    private final UsersPanel parentPanel;
    private final String filename;

    public UserPanel(UsersPanel panel, String filename, String username) {
        super(System.getProperty("file.separator") + filename);
        this.filename = filename;
        parentPanel = panel;
        JLabel name = ViewUtils.initLabel(username, 16, 200, 50, 70, 5);
        add(name);
        JButton button = ViewUtils.initButton(260, 63, 2, 2, e -> {
            makeChosenBackground();
            parentPanel.chooseDialog(username);
        });
        add(button);
    }

    public void makeChosenBackground() {
        this.setImageIcon(System.getProperty("file.separator") + filename.substring(0, filename.indexOf('.')) + "Chosen.png");
    }

    public void makeDefaultBackground() {
        this.setImageIcon(System.getProperty("file.separator") + filename);
    }
}
