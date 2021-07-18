import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class PeerThread extends Thread {
    BufferedReader reader;
    Socket socket;
    DataOutputStream stream;
    Gson gson = new Gson();

    public PeerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.stream = new DataOutputStream(
                this.socket.getOutputStream());
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                String message = this.reader.readLine();
                HashMap<String, String> json = this.gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
                }.getType());
                System.out.println("[" + json.get("username") + "]: " + json.get("message"));
            } catch (Exception e) {
                flag = false;
                interrupt();
            }
        }
    }
}
