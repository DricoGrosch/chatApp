package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket client, ArrayList<ClientHandler> clients) throws IOException {
        this.client = client;
        this.clients = clients;
        this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.out = new PrintWriter(this.client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
//                recebe a mensagem do cliente e manda para todos os clientes da lista
                String clientMessage = this.in.readLine();
                outToAll(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.out.close();
            try {
                this.in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String message) {
        for (ClientHandler clientHandler : this.clients) {
            clientHandler.out.println(message);
        }
    }
}
