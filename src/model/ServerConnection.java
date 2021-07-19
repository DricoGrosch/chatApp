package model;

import view.ChatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection extends Thread {
    private Socket server;
    private BufferedReader in;
    ChatView chat;

    public ServerConnection(Socket server, ChatView chat) throws IOException {
        this.server = server;
        this.chat = chat;
        this.in = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
//               fica esperando uma responta do servidor para poder printar na tela
                String serverResponse = null;
                serverResponse = this.in.readLine();
                if (serverResponse == null) {
                    break;
                }
                this.chat.getMessages().setText(this.chat.getMessages().getText() + "\n" + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
