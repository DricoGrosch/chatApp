package model;

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
    private static ArrayList<String> ports = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(Server.PORT);
        System.out.println("[SERVER] is waiting for client connection");
        while (true) {
            Socket client = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            JSONObject request = new JSONObject(in.readLine());
            JSONObject response = new JSONObject();
            if (request.getString("message").equals("getPorts")) {
                JSONArray array = new JSONArray(ports);
                response.put("ports", array.toString().toString());
            } else {
                ports.add(request.getString("message"));
                response.put("authenticated", true);
            }
            out.println(response.toString());
        }

    }
}
