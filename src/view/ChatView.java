package view;

import model.Client;
import model.MessageListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
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

    public JTextArea getMessages() {
        return messages;
    }

    public ChatView(String username, String host, int port) throws IOException {
        super("Chat " + username);
        this.client = new Client(username);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.mainFrame);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//        this.pack();
        connect(host, port);
        sendMessageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendMessage();
            }
        });
        messageInput.addKeyListener(new KeyAdapter() {
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
        out.println("[" + this.client.getName() + "] " + message);
    }

    private void connect(String host, int port) throws IOException {
        Socket socket = null;
        socket = new Socket(host, port);
        MessageListener messageListener = new MessageListener(socket, this);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        messageListener.start();
        this.out.println(this.client.getName() + " got in");
    }
}


//socket.close();
//System.exit(0);