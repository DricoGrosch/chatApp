import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<>();

    public ServerThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Set<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }

    @Override
    public void run() {
        try {
            ServerThreadThread serverThreadThread = new ServerThreadThread(this, this.serverSocket.accept());
            this.serverThreadThreads.add(serverThreadThread);
            serverThreadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            this.serverThreadThreads.forEach(t -> t.getWriter().println(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
