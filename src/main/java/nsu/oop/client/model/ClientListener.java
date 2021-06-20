package nsu.oop.client.model;


import java.util.List;

public interface ClientListener {
    void setUsername(String username);

    void updateUserList(List<String> list);

    void closeChatSession();

    void updateChat(String chatName, String message);

    void handleAnError(String error);
}
