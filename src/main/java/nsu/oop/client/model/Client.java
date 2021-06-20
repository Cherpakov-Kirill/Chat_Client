package nsu.oop.client.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String inputMessage;
    private String login;
    private final ClientListener listener;
    private UpdaterThread updaterThread;

    public Client(ClientListener listener, String ipAddress, int port) {
        this.listener = listener;
        try {
            this.socket = new Socket(ipAddress, port);
        } catch (IOException e) {
            System.err.println("ERROR: Socket failed");
        }
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            closeSocket();
        }
    }

    private void readMessage() throws IOException {
        try {
            inputMessage = in.readUTF();
        } catch (IOException exception) {
            updaterThread.interrupt();
            closeSocket();
            throw new IOException("ERROR with reading from server");
        }
    }

    public void sendMessage(String message) throws IOException {
        if (message.isEmpty()) return;
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            updaterThread.interrupt();
            closeSocket();
            throw new IOException("ERROR with sending to server");
        }
    }

    public void startRegistration(String login, String pass) {
        try {
            readMessage();
            if (inputMessage.startsWith("Server needs a authentication")) {
                sendMessage("/registration: " + login + " " + pass);
                readMessage();
                if (inputMessage.startsWith("/registration_success")) {
                    this.login = login;
                    updaterThread = new UpdaterThread();
                    updaterThread.start();
                } else if (inputMessage.startsWith("/registration_failed")) {
                    System.err.println(inputMessage);
                    listener.handleAnError(inputMessage.substring(inputMessage.indexOf(" ") + 1));
                } else {
                    System.err.println("Bad server-protocol. I waited accept of the registration!");
                    shutdownWorking();
                }
            } else {
                System.err.println("Bad server-protocol. I waited server-waiting-auth message!");
                listener.handleAnError("Bad server-protocol. I waited server-waiting-auth message!");
                shutdownWorking();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void startAuthentication(String login, String pass) {
        try {
            readMessage();
            if (inputMessage.startsWith("Server needs a authentication")) {
                sendMessage("/auth " + login + " " + pass);
                readMessage();
                if (inputMessage.startsWith("/auth_success")) {
                    this.login = login;
                    updaterThread = new UpdaterThread();
                    updaterThread.start();
                    listener.setUsername(login);
                } else if (inputMessage.startsWith("/auth_failed")) {
                    System.err.println(inputMessage);
                    listener.handleAnError(inputMessage.substring(inputMessage.indexOf(" ") + 1));
                } else {
                    System.err.println("Bad server-protocol. I waited accept of the authentication!");
                    listener.handleAnError("Bad server-protocol. I waited accept of the authentication!");
                    shutdownWorking();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public class UpdaterThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    readMessage();
                    if (inputMessage.startsWith("/end")) {
                        String[] elements = inputMessage.split(" ");
                        String name = elements[1];
                        if (name.equals(login)) {
                            listener.updateChat("Group Chat", "Good bye, " + name + "!");
                            listener.closeChatSession();
                            closeSocket();
                            break;
                        } else {
                            listener.updateChat("Group Chat", name + " " + "left the Chat!");
                        }
                        continue;
                    } else if (inputMessage.startsWith("/delete_account")) {
                        listener.updateChat("Group Chat", "Good bye!!");
                        closeSocket();
                        listener.closeChatSession();
                        break;
                    } else if (inputMessage.startsWith("/user_list:")) {
                        String[] users = inputMessage.split(" ");
                        StringBuilder userList = new StringBuilder();
                        userList.append("Online users: ");
                        List<String> otherOnlineUsers = new ArrayList<>();
                        for (int i = 1; i < users.length; i++) {
                            userList.append(" ").append(users[i]).append(",");
                            if (!users[i].equals(login)) otherOnlineUsers.add(users[i]);
                        }
                        listener.updateUserList(otherOnlineUsers);
                        userList.setLength(userList.length() - 1);
                        listener.updateChat("Group Chat", userList.toString());
                        continue;
                    } else if (inputMessage.startsWith("/private_message")) {
                        String[] elements = inputMessage.split(" ");
                        String name = elements[1];
                        String message = name + " : " + elements[3];
                        if (!name.equals(login)) {
                            listener.updateChat(name, message);
                        }
                        continue;
                    }
                    System.out.println(inputMessage);
                    listener.updateChat("Group Chat", inputMessage);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void closeSocket() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
    }

    public void shutdownWorking() {
        try {
            sendMessage("/end");
            updaterThread.interrupt();
            closeSocket();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
