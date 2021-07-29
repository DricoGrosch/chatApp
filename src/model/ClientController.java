package model;

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

    public ClientController(Client client, ChatView view) {
        this.client = client;
        this.view = view;
    }

    public void sendMessage(String message) throws IOException {
        JSONArray ports = new JSONArray(this.retrieveDataFromServer("getPorts").getString("ports"));
        for (Object port : ports) {
            int intPort = Integer.parseInt(port.toString());
            if (intPort == this.client.getPort()) {
                continue;
            }
            Socket socket = new Socket(this.client.getHost(), intPort);
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
        Socket socket = new Socket(this.client.getHost(), Server.PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JSONObject json = new JSONObject();
        json.put("name", this.client.getName());
        json.put("message", message);
        out.println(json.toString());
        JSONObject response = new JSONObject(in.readLine());
        return response;
    }

    public void connect() throws IOException {
        this.retrieveDataFromServer(this.client.getPort() + "");
        ClientServer server = new ClientServer(this.client.getPort(), this.view);
        server.start();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
