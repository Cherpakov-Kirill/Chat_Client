package nsu.oop.client.view.windows;

import nsu.oop.client.presenter.Presenter;
import nsu.oop.client.view.View;
import nsu.oop.client.view.panels.chat.ChatListener;
import nsu.oop.client.view.panels.chat.ChatPanel;
import nsu.oop.client.view.panels.menu.MenuListener;
import nsu.oop.client.view.panels.menu.MenuPanel;
import nsu.oop.client.view.panels.start.StartListener;
import nsu.oop.client.view.panels.start.StartPanel;
import nsu.oop.client.view.panels.users.UsersListener;
import nsu.oop.client.view.panels.users.UsersPanel;
import nsu.oop.client.view.windows.authentication.Authentication;
import nsu.oop.client.view.windows.authentication.AuthenticationListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame implements View, AuthenticationListener, StartListener, ChatListener, UsersListener, MenuListener {
    private static final String NAME = "Chat";
    private static final String MENU = "Menu";
    private static final String LOG_OUT = "Log out";
    private static final String EXIT = "Exit";
    private String chosenDialog;
    private String username;

    private Authentication authentication;

    private final StartPanel startPanel;
    private UsersPanel usersPanel;
    private MenuPanel menuPanel;
    private Presenter presenter;
    private Map<String, ChatPanel> allChatPanels;

    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU);
        menu.add(createMenuItem(LOG_OUT, e -> logOut()));
        menu.add(createMenuItem(EXIT, e -> closeTheChat()));
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    public MainWindow() {
        super(NAME);
        allChatPanels = new HashMap<>();
        this.setSize(750, 808);
        setupMenu();
        startPanel = new StartPanel(this);
        this.setContentPane(startPanel);
        this.repaint();
        this.setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeTheChat();
            }
        });
    }

    private JPanel getContentPanel(JPanel bar, ChatPanel chatPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(bar);
        panel.add(chatPanel);
        return panel;
    }

    private void setContentOnFrame(Container pane) {
        this.setContentPane(pane);
        this.repaint();
        this.setVisible(true);
    }

    private void startTheChat() {
        presenter.createClient();
        usersPanel = new UsersPanel(this);
        ChatPanel chatPanel = initChatPanel("Group Chat");
        allChatPanels.put("Group Chat", chatPanel);
        setContentOnFrame(getContentPanel(usersPanel, chatPanel));
        chosenDialog = "Group Chat";
    }

    ///View
    @Override
    public void attachPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void changeVisible(boolean var) {
        this.setVisible(var);
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        menuPanel = new MenuPanel(this, username);
    }

    @Override
    public void updateChatField(String chatName, String message) {
        allChatPanels.get(chatName).addNewMessage(message);
    }

    @Override
    public void handleAnError(String error) {
        this.setVisible(false);
        JOptionPane.showMessageDialog(this,
                error,
                "Error", JOptionPane.INFORMATION_MESSAGE,
                null);
        closeTheChat();
    }

    private ChatPanel initChatPanel(String destinationUser) {
        ChatPanel chatPanel = new ChatPanel(this, username, destinationUser);
        chatPanel.setPreferredSize(new Dimension(488, 808));
        Dimension chatPanelSize = chatPanel.getPreferredSize();
        chatPanel.setBounds(262, 0, chatPanelSize.width, chatPanelSize.height);
        return chatPanel;
    }

    @Override
    public void updateUserList(List<String> list) {
        usersPanel.updateUserList(list);
        List<String> users = allChatPanels.keySet().stream().toList();
        for (String name : list) {
            if (!allChatPanels.containsKey(name)) {
                allChatPanels.put(name, initChatPanel(name));
            } else {
                users.remove(name);
            }

        }
        for (String name : users) {
            if (name.equals("Group Chat")) continue;
            allChatPanels.remove(name);
        }
    }

    @Override
    public void closeChatSession() {
        setContentOnFrame(startPanel);
    }

    ///StartListener
    @Override
    public void makeLogIn() {
        authentication = new Authentication("Login", this);
    }

    @Override
    public void makeRegistration() {
        authentication = new Authentication("Registration", this);
    }

    //MenuListener
    @Override
    public void closeMenuBar() {
        setContentOnFrame(getContentPanel(usersPanel, allChatPanels.get(chosenDialog)));
    }

    @Override
    public void logOut() {
        presenter.endTheSession();
        closeChatSession();
    }

    @Override
    public void deleteAccount() {
        sendNewMessage("/delete_account");
    }

    @Override
    public void closeTheChat() {
        presenter.endTheSession();
        System.exit(0);
    }

    ///AuthenticationListener
    @Override
    public void login() {
        authentication.setVisible(false);
        startTheChat();
        presenter.startAuthentication(authentication.getUsername(), authentication.getPass());
    }

    @Override
    public void registration() {
        authentication.setVisible(false);
        startTheChat();
        presenter.startRegistration(authentication.getUsername(), authentication.getPass());
    }

    ///ChatPanel
    @Override
    public void sendNewMessage(String newMessage) {
        closeMenuBar();
        presenter.sendNewMessage(newMessage);
    }

    ///UsersListener
    @Override
    public void chooseTheDialog(String username) {
        setContentOnFrame(getContentPanel(usersPanel, allChatPanels.get(username)));
        chosenDialog = username;
    }

    @Override
    public void openMenuBar() {
        setContentOnFrame(getContentPanel(menuPanel, allChatPanels.get(chosenDialog)));
    }
}
