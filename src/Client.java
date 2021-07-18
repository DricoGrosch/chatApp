import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String name;

    public String getName() {
        return name;
    }

    public Client(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9090);
        ServerConnection serverConnection = new ServerConnection(socket);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type client name");
        Client c = new Client(keyboard.readLine());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        serverConnection.start();
        out.println(c.getName() + " got in");
        while (true) {
            System.out.println("> ");
            String message = keyboard.readLine();
            if (message.equals("quit")) {
                break;
            }
            out.println("[" + c.getName() + "] " + message);
        }
        socket.close();
        System.exit(0);
    }
}
