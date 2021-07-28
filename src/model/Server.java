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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //    uma lista com todas as threas dos clientes. assim o servidor sabe pra quem tem que encaminhar as mensagens as mensagens
    private static ArrayList<String> ports = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8000);
        System.out.println("[SERVER] is waiting for client connection");
        while (true) {
            Socket client = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            JSONObject request = new JSONObject(in.readLine());
            JSONObject response = new JSONObject();
            if (request.getString("message").equals("getPorts")) {
                JSONArray array = new JSONArray(ports);
                String a = array.toString().toString();
                response.put("ports",a );
            } else {
                ports.add(request.getString("message"));
                response.put("ok", true);
            }
            out.println(response.toString());
        }

    }
}
