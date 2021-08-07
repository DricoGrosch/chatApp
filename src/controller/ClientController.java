package controller;

import model.Client;
import model.ClientServer;
import model.Server;
import org.json.JSONArray;
import org.json.JSONObject;
import view.ChatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {
    private final ChatView view;
    private Client client;
    private String serverHost;
    private int serverPort;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public ClientController(Client client, String serverHost, int serverPort, ChatView view) {
        this.client = client;
        this.view = view;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void sendMessage(String message) throws IOException {
        JSONArray clients = new JSONArray(this.retrieveDataFromServer("getClients").getString("clients"));
        for (int i = 0; i < clients.length(); i++) {
            JSONObject client = clients.getJSONObject(i);
            int port = client.getInt("port");
            if (port == this.client.getPublicPort()) {
                continue;
            }
            Socket socket = new Socket(client.getString("host"), port);
            JSONObject json = new JSONObject();
            json.put("name", this.client.getName());
            json.put("message", message);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in.readLine();
            socket.close();
            out.close();
        }
    }

    public JSONObject retrieveDataFromServer(String message) throws IOException {
        Socket socket = new Socket(this.getServerHost(), this.getServerPort());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JSONObject json = new JSONObject();
        json.put("name", "connect");
        json.put("message", message);
        out.println(json.toString());
        JSONObject response = new JSONObject(in.readLine());
        return response;
    }

    public void connect() throws IOException {
        JSONObject json = new JSONObject();
        json.put("host", this.client.getHost());
        json.put("port", this.client.getPublicPort());
        this.retrieveDataFromServer(json.toString());
        ClientServer server = new ClientServer(this.client, this.view);
        server.start();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
