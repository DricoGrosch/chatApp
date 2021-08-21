package view;

import model.Client;
import model.MessageListener;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class ChatView extends JFrame {
    private JTextArea messages;
    private JTextField messageInput;
    private JButton sendMessageBtn;
    private JPanel mainFrame;
    private JScrollPane scrollPane;
    private Client client;
    PrintWriter out;

    public Client getClient() {
        return client;
    }

    public JTextArea getMessages() {
        return messages;
    }

    public ChatView(String username, String host, int port) throws IOException {
        super("Chat " + username);
        try {
            this.client = new Client(username);
            this.connect(host, port);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setContentPane(this.mainFrame);
            this.setLocationRelativeTo(null);
            this.setSize(500, 500);
            this.setResizable(false);
            this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.atachListeners();
//            this.setVisible(true);
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
        System.out.println("SEND >>>" + LocalDateTime.now());
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

    private void connect(String host, int port) throws IOException {
        Socket socket = null;
        socket = new Socket(host, port);
        MessageListener messageListener = new MessageListener(socket, this);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        messageListener.start();
        JSONObject json = new JSONObject();
        json.put("name", this.client.getName());
        json.put("message", "got in");
        this.out.println(json.toString());
    }
}


//socket.close();
//System.exit(0);