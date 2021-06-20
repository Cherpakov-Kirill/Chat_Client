package nsu.oop.client.view.panels.menu;

import nsu.oop.client.view.ViewUtils;
import nsu.oop.client.view.panels.WindowPanel;

import java.awt.*;

public class MenuPanel extends WindowPanel {
    public MenuPanel(MenuListener listener, String username) {
        super(System.getProperty("file.separator") + "Menu.png");
        add(ViewUtils.initButton(40, 40, 5, 5, e -> listener.closeMenuBar()));
        add(ViewUtils.initButton(210, 35, 20, 180, e -> listener.logOut()));
        add(ViewUtils.initButton(210, 35, 20, 230, e -> listener.deleteAccount()));
        add(ViewUtils.initButton(210, 35, 20, 280, e -> listener.closeTheChat()));
        add(ViewUtils.initLabel(username, 18, 150, 30, 90, 90));
        setPreferredSize(new Dimension(262, 808));
        Dimension usersPanelSize = getPreferredSize();
        setBounds(0, 0, usersPanelSize.width, usersPanelSize.height);
    }
}
