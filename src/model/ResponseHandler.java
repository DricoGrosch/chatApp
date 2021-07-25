package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ResponseHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ResponseHandler> clients;

    public ResponseHandler(Socket client, ArrayList<ResponseHandler> clients) throws IOException {
        this.client = client;
        this.clients = clients;
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
        for (ResponseHandler responseHandler : this.clients) {
            if (responseHandler.getClient().hashCode() != this.getClient().hashCode()) {
                responseHandler.out.println(message);
            }
        }
    }
}
