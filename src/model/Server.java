package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //    uma lista com todas as threas dos clientes. assim o servidor sabe pra quem tem que encaminhar as mensagens as mensagens
    private static ArrayList<ResponseHandler> clients = new ArrayList<>();
    //    o executor é um facilitador na hora de executar as threads dinamicamente
    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8000);
        System.out.println("[SERVER] is waiting for client connection");
        while (true) {
            Socket client = listener.accept();
//            um cliente se conectou com o servidor
            ResponseHandler clientThread = new ResponseHandler(client, clients);
//            esse cliente é adicionado na lista de clientes e  depois é executada a thread
            clients.add(clientThread);
            executor.execute(clientThread);
        }
    }
}
