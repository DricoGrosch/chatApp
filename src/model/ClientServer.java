package model;

import org.json.JSONObject;
import view.ChatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer extends Thread {
    private ChatView view;
    private Client client;
    private BufferedReader in;

    public ClientServer(Client client, ChatView view) {
        this.client = client;
        this.view = view;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(this.client.getPrivatePort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket client = server.accept();
                this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                String clientMessage = this.in.readLine();
                if (clientMessage == null || clientMessage.equals("null")) {
                    break;
                }
                JSONObject json = new JSONObject(clientMessage);
                this.view.getMessages().setText(this.view.getMessages().getText() + "\n" + "[" + json.getString("name") + "] " + json.getString("message"));
                JSONObject response = new JSONObject();
                response.put("received", true);
                out.println(response.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
