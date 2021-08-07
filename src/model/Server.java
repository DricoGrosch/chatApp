package model;

import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 8000;
    private static JSONArray clients = new JSONArray();

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(Server.PORT);
        System.out.println("[SERVER] is waiting for client connection");
        while (true) {
            Socket client = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            JSONObject request = new JSONObject(in.readLine());
            JSONObject response = new JSONObject();
            if (request.getString("message").equals("getClients")) {
                response.put("clients", clients.toString());
            } else {
                JSONObject clientData = new JSONObject(request.getString("message"));
                clients.put(clientData);
                response.put("authenticated", true);
            }
            out.println(response.toString());
        }

    }
}
