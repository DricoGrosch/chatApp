package controller;

import model.Client;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientPingController extends Thread {
    private Client client;

    public ClientPingController(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Socket socket = new Socket(this.client.getServerHost(), this.client.getServerPort());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    JSONObject json = new JSONObject();
                    json.put("name", "ping");
                    json.put("message", this.client.toJson());
                    out.println(json.toString());
                    socket.close();
                    out.close();
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {

        }
    }
}
