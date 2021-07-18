import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThreadBuffer {
    private ArrayList<ServerThread> threads;
    private static ServerThreadBuffer instance = null;

    public ArrayList<ServerThread> getThreads() {
        return threads;
    }

    public static ServerThreadBuffer getInstance() {
        if (instance == null) {
            instance = new ServerThreadBuffer();
            instance.threads = new ArrayList<>();
        }
        return instance;
    }

    public void addThread(ServerThread t) {
        this.threads.add(t);
    }
}
