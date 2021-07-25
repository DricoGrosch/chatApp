package view;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        this.atachlisteners();

    }

    private boolean validateInputs() {
        try {
            Integer.parseInt(port.getText());
            return !username.getText().equals("") && !host.getText().equals("");
        } catch (Exception e) {
            return false;
        }
    }

    private void atachlisteners() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateInputs()) {
                    return;
                }
                LoginView.super.setVisible(false);
                try {
                    new ChatView(username.getText(), host.getText(), Integer.parseInt(port.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                port.setText("");
                host.setText("");
            }
        });
    }
}
