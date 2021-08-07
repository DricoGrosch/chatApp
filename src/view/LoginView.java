package view;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginView extends JFrame {
    private JPanel mainFrame;
    private JTextField username;
    private JButton cancelButton;
    private JTextField serverPort;
    private JTextField serverHost;
    private JButton submitButton;
    private JTextField host;
    private JTextField publicPort;
    private JTextField privatePort;

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
            Integer.parseInt(serverPort.getText());
            Integer.parseInt(publicPort.getText());
            Integer.parseInt(privatePort.getText());
            return !username.getText().equals("") && !serverHost.getText().equals("") && !host.getText().equals("");
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
                    new ChatView(username.getText(), serverHost.getText(), Integer.parseInt(serverPort.getText()), host.getText(), Integer.parseInt(privatePort.getText()), Integer.parseInt(publicPort.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                serverPort.setText("");
                serverHost.setText("");
            }
        });
    }
}
