package nsu.oop.client.view.panels.users;

import nsu.oop.client.view.ViewUtils;
import nsu.oop.client.view.panels.WindowPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersPanel extends WindowPanel {
    private final UsersListener listener;
    private final JScrollPane scrollPane;
    private final Map<String, UserPanel> userPanelsMap;
    private String chosenDialog;

    public UsersPanel(UsersListener listener) {
        super(System.getProperty("file.separator") + "UsersField.png");
        userPanelsMap = new HashMap<>();
        this.listener = listener;
        scrollPane = ViewUtils.initScrollPane(260, 673, 5, 75);
        add(scrollPane);
        add(ViewUtils.initButton(40, 40, 12, 12, e -> listener.openMenuBar()));
        setPreferredSize(new Dimension(262, 808));
        Dimension usersPanelSize = getPreferredSize();
        setBounds(0, 0, usersPanelSize.width, usersPanelSize.height);
    }

    public void chooseDialog(String name) {
        if (chosenDialog != null) {
            if (chosenDialog.equals(name)) return;
            userPanelsMap.get(chosenDialog).makeDefaultBackground();
        }
        chosenDialog = name;
        listener.chooseTheDialog(name);
    }

    public void updateUserList(List<String> list) {
        boolean isChosenAnyDialog = false;
        JPanel usersPanel = new JPanel();
        usersPanel.setOpaque(false);
        usersPanel.setLayout(null);
        UserPanel group = new UserPanel(this, "Group.png", "Group Chat");
        if (chosenDialog == null || chosenDialog.equals("Group Chat")) {
            chosenDialog = "Group Chat";
            group.makeChosenBackground();
            isChosenAnyDialog = true;
        }
        userPanelsMap.put("Group Chat", group);
        group.setPreferredSize(new Dimension(262, 65));
        Dimension groupSize = group.getPreferredSize();
        group.setBounds(0, 0, groupSize.width, groupSize.height);
        usersPanel.add(group);
        int number = 1;
        for (String name : list) {
            UserPanel user = new UserPanel(this, "User.png", name);
            if (chosenDialog.equals(name)) {
                user.makeChosenBackground();
                isChosenAnyDialog = true;
            }
            userPanelsMap.put(name, user);
            user.setPreferredSize(new Dimension(262, 65));
            Dimension userSize = user.getPreferredSize();
            user.setBounds(0, number * 65, userSize.width, userSize.height);
            usersPanel.add(user);
            number++;
        }
        scrollPane.setViewportView(usersPanel);
        if (!isChosenAnyDialog) {
            chosenDialog = "Group Chat";
            group.makeChosenBackground();
            listener.chooseTheDialog(chosenDialog);
        }
    }
}
