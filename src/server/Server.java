package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    int port;
    HashMap<String, Socket> clients;
    Gson gson = new Gson();

    public Server(int port) {
        this.clients = new HashMap<>();
        this.port = port;
        this.start();
    }

    public ArrayList<String> getClients() {
//        osh que doido
        return new ArrayList<>(this.clients.keySet());
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                System.out.println("About to accept client connection...");
                Socket client = serverSocket.accept();
                System.out.println("Accepted connection from " + client);
                this.clients.put(client.getLocalAddress().getHostAddress(), client);
                this.addMessageListener(client);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> jsonLoads(String json) {
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void removeClient(String ip) {
        this.clients.remove(ip);
    }

    public void addMessageListener(Socket client) throws IOException {
        DataInputStream stream = new DataInputStream(client.getInputStream());
        PrintStream ps = new PrintStream(client.getOutputStream());

        while (true) {
            String message = stream.readUTF();
            HashMap<String, String> query = this.jsonLoads(message);
            if (query.get("operation").equals("get")) {
                ArrayList<String> clients = this.getClients();
                String strClients = this.gson.toJson(clients);
                ps.println(strClients);
            } else if (query.get("operation").equals("remove")) {
                this.removeClient(query.get("ip"));
            }
        }


    }
}
