import view.ChatView;
import view.LoginView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            ChatView c = new ChatView("usuario" + i, "127.0.0.1", 8000);
            if (i == 0) {
                c.setVisible(true);
            }
        }

//        LoginView login = new LoginView();
//        login.setVisible(true);
    }

}
