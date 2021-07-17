package client.view;

import javax.swing.*;
import java.awt.*;
import java.util.logging.XMLFormatter;

public class LoginView extends JFrame {
    private JPanel mainFrame;
    private JTextField username;
    private JButton cancelButton;
    private JTextField port;
    private JTextField host;
    private JButton submitButton;

    public LoginView() {
        super("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.mainFrame);
        this.setLocationRelativeTo(null);
        this.pack();
    }

}
