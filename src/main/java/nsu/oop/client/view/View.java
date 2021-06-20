package nsu.oop.client.view;

import nsu.oop.client.presenter.Presenter;

import java.util.List;

public interface View {
    void attachPresenter(Presenter presenter);

    void changeVisible(boolean var);

    void setUsername(String username);

    void updateChatField(String chatName, String message);

    void handleAnError(String error);

    void closeChatSession();

    void updateUserList(List<String> list);
}
