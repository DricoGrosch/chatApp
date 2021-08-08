package model;

import controller.ServerPingCountdown;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 8000;
    public static JSONArray clients = new JSONArray();
    private static ExecutorService executor = Executors.newFixedThreadPool(4);
    private ArrayList<ServerPingCountdown> pings = new ArrayList<>();

    public JSONObject getCLients() {
        JSONObject response = new JSONObject();
        response.put("clients", clients.toString());
        return response;
    }

    public JSONObject handleConnection(JSONObject clientData) {
        JSONObject response = new JSONObject();
        clients.put(clientData);
        response.put("authenticated", true);
        ServerPingCountdown serverPing = new ServerPingCountdown(clientData);
        this.pings.add(serverPing);
        serverPing.start();
        return response;

    }

    public JSONObject handlePing(JSONObject pingClient) {

        for (ServerPingCountdown p : this.pings) {
            if (p.getClient().toString().equals(pingClient.toString())) {
                p.resetTimer();
            }
        }
        return new JSONObject();

    }

    public void start() throws IOException {
        ServerSocket listener = new ServerSocket(Server.PORT);
        System.out.println("[SERVER] is waiting for client connection");

        while (true) {
            Socket client = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            JSONObject request = new JSONObject(in.readLine());
            JSONObject response = new JSONObject();
            if (request.getString("name").equals("getClients")) {
                response = this.getCLients();
            } else if (request.getString("name").equals("connect")) {
                response = this.handleConnection(request.getJSONObject("message"));
            } else if (request.getString("name").equals("ping")) {
                response = this.handlePing(request.getJSONObject("message"));
            }
            out.println(response.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
