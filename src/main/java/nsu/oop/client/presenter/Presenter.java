package nsu.oop.client.presenter;

import nsu.oop.client.model.Client;
import nsu.oop.client.model.ClientListener;
import nsu.oop.client.view.View;

import java.io.IOException;
import java.util.List;

public class Presenter implements ClientListener {
    private final View view;
    private Client client;
    public String ipAddress;
    public int port;

    public Presenter(View view, String ipAddress, int port) {
        this.view = view;
        this.view.attachPresenter(this);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void launchTheChat() {
        view.changeVisible(true);
    }

    public void createClient() {
        client = new Client(this, ipAddress, port);
    }

    public void startRegistration(String login, String pass) {
        client.startRegistration(login, pass);
    }

    public void startAuthentication(String login, String pass) {
        client.startAuthentication(login, pass);
    }

    public void endTheSession() {
        if (client != null) {
            client.shutdownWorking();
            client = null;
        }
    }

    @Override
    public void setUsername(String username) {
        view.setUsername(username);
    }

    @Override
    public void updateUserList(List<String> list) {
        view.updateUserList(list);
    }

    @Override
    public void closeChatSession() {
        view.closeChatSession();
    }

    @Override
    public void updateChat(String chatName, String message) {
        view.updateChatField(chatName, message);
    }

    @Override
    public void handleAnError(String error) {
        view.handleAnError(error);
    }

    public void sendNewMessage(String newMessage) {
        try {
            client.sendMessage(newMessage);
        } catch (IOException exception) {
            view.handleAnError(exception.getMessage());
        }
    }
}
