import view.ChatView;
import view.LoginView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (int i = 8001; i < 8101; i++) {
            ChatView c = new ChatView("usuario" + i, "127.0.0.1", 8000, "127.0.0.1", i, i);
            if (i == 8001) {
                c.setVisible(true);
            }
        }
    }

}
