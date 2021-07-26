package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ResponseHandler extends Server implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ResponseHandler(Socket client) throws IOException {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.out = new PrintWriter(this.client.getOutputStream(), true);
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            while (true) {
//                recebe a mensagem do cliente e manda para todos os clientes da lista
                String clientMessage = this.in.readLine();
                if (clientMessage == null || clientMessage.equals("null")) {
                    break;
                }
                outToAll(clientMessage);
            }
        } catch (IOException e) {
        }
    }

    private void outToAll(String message) {
        for (ResponseHandler responseHandler : Server.getClients()) {
            if (responseHandler.getClient().hashCode() != this.getClient().hashCode()) {
                responseHandler.out.println(message);
            }
        }
    }
}
