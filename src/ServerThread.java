import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private PrintWriter writer;
    ServerThreadBuffer buffer;

    public ServerThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            Socket s = this.serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.writer = new PrintWriter(s.getOutputStream(), true);
            while (true) {
                this.sendMessage(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            ArrayList<ServerThread> a = this.buffer.getInstance().getThreads();
            for (ServerThread t : a) {
//                if (t.hashCode() != this.hashCode()) {
                    t.writer.println(message);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
