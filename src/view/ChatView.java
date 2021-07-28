package view;

import model.Client;
import model.ClientServer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatView extends JFrame {
    private JTextArea messages;
    private JTextField messageInput;
    private JButton sendMessageBtn;
    private JPanel mainFrame;
    private JScrollPane scrollPane;
    private Client client;
    PrintWriter out;
    private int port;
    private String host;

    public JTextArea getMessages() {
        return messages;
    }

    public ChatView(String username, String host, int port) throws IOException {
        super("Chat " + username);
        try {
            this.port = port;
            this.host = host;
            this.client = new Client(username);
            this.connect();
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
                sendMessage();
            }
        });
        this.messageInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage() {
        String message = messageInput.getText();
        messageInput.setText("");
        if (!message.trim().equals("")) {
            this.getMessages().setText(this.getMessages().getText() + "\n" + "[me] " + message);
            JSONObject json = new JSONObject();
            json.put("name", this.client.getName());
            json.put("message", message);
            this.out.println(json.toString());
        }
    }

    private void connect() throws IOException {
        Socket socket = new Socket(this.host, 8000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JSONObject json = new JSONObject();
        json.put("name", this.client.getName());
        json.put("message", "login");
        out.println(json.toString());
        String response = in.readLine();
        ClientServer server = new ClientServer(this.port,this);
        server.start();
    }
}