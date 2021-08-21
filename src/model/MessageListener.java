package model;

import org.json.JSONObject;
import view.ChatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;

public class MessageListener extends Thread {
    private Socket server;
    private BufferedReader in;
    ChatView chat;

    public MessageListener(Socket server, ChatView chat) throws IOException {
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
                if (serverResponse == null || serverResponse.equals("null")) {
                    break;
                }
                JSONObject json = new JSONObject(serverResponse);
                this.chat.getMessages().setText(this.chat.getMessages().getText() + "\n" + "[" + json.getString("name") + "] " + json.getString("message"));
                System.out.println("RECEIVED " + this.chat.getClient().getName() + ">>>" + LocalDateTime.now());
            }
        } catch (IOException e) {
            System.out.println("deu pau");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("deu pau");
            }
        }
    }
}
