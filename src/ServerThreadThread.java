import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreadThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;
    private PrintWriter writer;

    public ServerThreadThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            while (true) {
                serverThread.sendMessage(reader.readLine());
            }
        } catch (Exception e) {
            this.serverThread.getServerThreadThreads().remove(this);
            e.printStackTrace();
        }
    }
}
