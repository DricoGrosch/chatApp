package controller;

import model.Client;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
                    json.put("name", this.client.getHost());
                    json.put("message", "ping");
                    out.println(json.toString());
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {

        }
    }
}
