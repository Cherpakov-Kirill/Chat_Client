package nsu.oop.client.view.panels.start;

import nsu.oop.client.view.ViewUtils;
import nsu.oop.client.view.panels.WindowPanel;

public class StartPanel extends WindowPanel {
    public StartPanel(StartListener listener) {
        super(System.getProperty("file.separator") + "Start.png");
        add(ViewUtils.initButton(305, 55, 225, 313, e -> listener.makeLogIn()));
        add(ViewUtils.initButton(305, 55, 225, 409, e -> listener.makeRegistration()));
    }
}
