import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Peer {
    BufferedReader reader;
    String username;
    String host;
    int port;
    Gson gson = new Gson();


    public Peer() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("username");
        this.username = this.reader.readLine();
        System.out.println("host");
        this.host = this.reader.readLine();
        System.out.println("port");
        this.port = Integer.parseInt(this.reader.readLine());
        ServerThread serverThread = new ServerThread(port);
        serverThread.start();
//        nessa parte aqui, precisa de um for. alguma classe tem que saber todo mundo que ta conectado
        for (int i = 5000; i < 5002; i++) {
            Socket socket=null;
            try {
                socket = new Socket(this.host, i);
                new PeerThread(socket).start();
                this.communicate(serverThread);
            } catch (Exception e) {
                if (socket != null) {
                    socket.close();
                } else {
                    System.out.println("Failed");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Peer();
    }

    public void communicate(ServerThread serverThread) {
        try {
            System.out.println("you can now send messages");
            boolean flag = true;
            while (flag) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", this.username);
                map.put("message", this.reader.readLine());
                String json = this.gson.toJson(map);
                serverThread.sendMessage(json);
            }
        } catch (Exception e) {

        }
    }

}
