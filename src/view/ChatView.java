package view;

import model.Client;
import controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ChatView extends JFrame {
    private JTextArea messages;
    private JTextField messageInput;
    private JButton sendMessageBtn;
    private JPanel mainFrame;
    private JScrollPane scrollPane;
    private ClientController clientController;


    public JTextArea getMessages() {
        return messages;
    }

    public ChatView(String username, String serverHost, int serverPort, String host, int privatePort, int publicPort) throws IOException {
        super("Chat " + username);
        try {
            this.clientController = new ClientController(new Client(username, privatePort, publicPort, host, serverHost, serverPort), serverHost, serverPort, this);
            this.clientController.connect();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setContentPane(this.mainFrame);
            this.setLocationRelativeTo(null);
            this.setSize(500, 500);
            this.setResizable(false);
            this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.atachListeners();
            this.setVisible(true);
        } catch (Exception e) {
            int res = JOptionPane.showOptionDialog(this, "Could not connect to server", "Server error", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, null, null);
            new LoginView().setVisible(true);
        }
    }

    private void atachListeners() {
        this.sendMessageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String message = messageInput.getText();
                    messageInput.setText("");
                    if (!message.trim().equals("")) {
                        getMessages().setText(getMessages().getText() + "\n" + "[me] " + message);
                        clientController.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.messageInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        String message = messageInput.getText();
                        messageInput.setText("");
                        if (!message.trim().equals("")) {
                            getMessages().setText(getMessages().getText() + "\n" + "[me] " + message);
                            clientController.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}