package nsu.oop.client.view.panels.chat;

import nsu.oop.client.view.ViewUtils;
import nsu.oop.client.view.panels.WindowPanel;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends WindowPanel {
    private final String username;
    private final String chatName;
    private final JTextArea textArea;
    private final JButton sendButton;
    private final JTextField messageField;
    private final ChatListener listener;

    private void sendMessage() {
        if (!messageField.getText().trim().isEmpty()) {
            String message;
            if (!chatName.equals("Group Chat")) {
                message = "/private_message " + chatName + " " + messageField.getText();
                addNewMessage(username + " : " + messageField.getText());
            } else {
                message = messageField.getText();
            }
            listener.sendNewMessage(message);
            messageField.setText("");
            messageField.grabFocus();
        }
    }

    private void configureTextArea() {
        textArea.setFont(new Font("Roboto", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(null);
        textArea.setOpaque(false);
    }

    private void configureMessageField() {
        messageField.setFont(new Font("Roboto", Font.PLAIN, 13));
        messageField.setCaretColor(Color.WHITE);
        messageField.setSelectionColor(Color.GRAY);
        messageField.setDisabledTextColor(Color.WHITE);
        messageField.setSelectedTextColor(Color.GRAY);
        messageField.setForeground(Color.WHITE);
        messageField.setBorder(null);
        messageField.setOpaque(false);
        messageField.setPreferredSize(new Dimension(410, 50));
        Dimension messageFieldSize = messageField.getPreferredSize();
        messageField.setBounds(10, 707, messageFieldSize.width, messageFieldSize.height);
        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                messageField.setText("");
            }
        });
        messageField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                            sendMessage();
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                }
        );
    }

    public ChatPanel(ChatListener listener, String username, String destination) {
        super(System.getProperty("file.separator") + "TextField.png");
        this.username = username;
        chatName = destination;
        this.listener = listener;
        textArea = new JTextArea();
        sendButton = ViewUtils.initButton(50, 50, 436, 707, e -> sendMessage());
        messageField = new JTextField("Type your message");
        configureTextArea();
        configureMessageField();
        add(ViewUtils.initLabel(destination, 18, 200, 30, 15, 20));
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = ViewUtils.initScrollPane(470, 620, 10, 80);
        scrollPane.setViewportView(textArea);

        add(scrollPane);
        add(sendButton);
        add(messageField);
    }

    public void addNewMessage(String message) {
        textArea.append(message);
        textArea.append("\n");
    }
}
