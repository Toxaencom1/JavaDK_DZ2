package client;

import account.Account;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientGUI extends JFrame implements ClientView {
    private static final int POS_X = 1920;
    private static final int POS_Y = 0;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    private final Client client;
    private JTextArea textArea;
    private JTextField textFieldLogin, textFieldIP, textFieldPort, passwordField, textFieldSend;
    private JPanel panelLogin;
    private JButton btnLogin;
    private JLabel loginName = new JLabel("");

    public ClientGUI(Server server) {
        this.client = new Client(this, server);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        setResizable(true);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        JPanel mainNorth = new JPanel(new GridLayout(1, 1));
        JPanel mainBottom = new JPanel(new GridLayout(1, 1));
        mainNorth.add(loginPassPanel());
        mainBottom.add(sendMessagePanel());
        add(mainNorth, BorderLayout.NORTH);
        add(scrollPane);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private Component loginPassPanel() {
        panelLogin = new JPanel(new GridLayout(2, 3));
        textFieldIP = new JTextField();
        textFieldIP.setText("127.0.0.1");
        textFieldPort = new JTextField();
        textFieldPort.setText("8080");
        textFieldLogin = new JTextField();
        textFieldLogin.setText("Anton");
        passwordField = new JPasswordField();
        passwordField.setText("1234");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            connectToServer();
        });
        panelLogin.add(textFieldIP);
        panelLogin.add(textFieldPort);
        panelLogin.add(loginName);
        panelLogin.add(textFieldLogin);
        panelLogin.add(passwordField);
        panelLogin.add(btnLogin);
        return panelLogin;
    }

    private Component sendMessagePanel() {
        JPanel panelSendMessage = new JPanel(new GridLayout(1, 2));
        JButton btnSend = new JButton("Send");
        textFieldSend = new JTextField();
        textFieldSend.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSend.doClick();
                    textFieldSend.setText("");
                }
            }
        });
        btnSend.addActionListener(e -> {
            sendMessage();
        });
        panelSendMessage.add(textFieldSend);
        panelSendMessage.add(btnSend);
        return panelSendMessage;
    }

    private void connectToServer() {
        if (client.isServerWorking()) {
            Account user = new Account();
            user.setIp(textFieldIP.getText());
            user.setPort(textFieldPort.getText());
            user.setName(textFieldLogin.getText());
            loginName.setText(textFieldLogin.getText());
            user.setPass(passwordField.getText());
            client.setUser(user);
            if (client.checkUserToConnect()) {
                client.setConnected(true);
                client.appendToList(this);
                client.sendMessageToTempLog("User " + client.getUser().getName() + " is connected\n");
                panelLogin.setVisible(false);
                textArea.setText(client.getHistory());
                JOptionPane.showMessageDialog(textArea, "You are logged in as: " + user.getName());
            } else {
                JOptionPane.showMessageDialog(panelLogin, "Login or password do not match");
            }
        } else {
            JOptionPane.showMessageDialog(panelLogin, "Server is offline");
        }
    }

    private void sendMessage() {
        if (client.isServerWorking() && client.isConnected()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            String formattedMessage = String.format(formattedDate + " "
                    + client.getUser().getName() + ": "
                    + textFieldSend.getText() + "\n");
            client.sendMessage(formattedMessage);
            client.sendMessageToTempLog(formattedMessage);
            textFieldSend.setText("");
        } else {
            JOptionPane.showMessageDialog(panelLogin, "Connection failed");
        }
    }

    @Override
    public void printMessage(String text) {
        textArea.append(text);
    }

    @Override
    public void disconnect() {
        client.disconnect();
        panelLogin.setVisible(true);
    }

    @Override
    public String getLoginName() {
        return loginName.getText();
    }
}
