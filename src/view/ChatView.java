package view;

import model.Client;
import model.ClientServer;
import org.json.JSONArray;
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
                try {
                    sendMessage();
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
                        sendMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendMessage() throws IOException {
        String message = messageInput.getText();
        messageInput.setText("");
        if (!message.trim().equals("")) {
            this.getMessages().setText(this.getMessages().getText() + "\n" + "[me] " + message);
            JSONArray ports = new JSONArray(this.retrieveDataFromServer("getPorts").getString("ports"));
            for (Object port : ports) {
                int intPort = Integer.parseInt(port.toString());
                if (intPort == this.port){
                    continue;
                }
                Socket socket = new Socket(this.host, intPort);
                JSONObject json = new JSONObject();
                json.put("name", this.client.getName());
                json.put("message", message);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(json.toString());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                in.readLine();
                socket.close();
                out.close();
            }
        }
    }

    public JSONObject retrieveDataFromServer(String message) throws IOException {
        Socket socket = new Socket(this.host, 8000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JSONObject json = new JSONObject();
        json.put("name", this.client.getName());
        json.put("message", message);
        out.println(json.toString());
        JSONObject response = new JSONObject(in.readLine());
        return response;
    }

    private void connect() throws IOException {
        this.retrieveDataFromServer(this.port + "");
        ClientServer server = new ClientServer(this.port, this);
        server.start();
    }
}