package client;

import account.Account;
import exceptions.AlreadyLoggedEx;
import exceptions.FileProblemsEx;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientGUI extends JFrame implements ClientView {
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String DEFAULT_PORT = "8080";
    private static final int POS_X = 1920;
    private static final int POS_Y = 0;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    private final Client client;
    private JTextArea textArea;
    private JTextField textFieldLogin, textFieldIP, textFieldPort, passwordField, textFieldSend;
    private JPanel panelLogin, mainNorth, discPanel;
    private JButton btnLogin;
    private JLabel loginName;
    Component loginPassPanel;
    Component disconnectPanel;

    /**
     * Constructs a new `ClientGUI` with the specified server.
     *
     * @param server The server to connect to.
     */
    public ClientGUI(Server server) {
        this.client = new Client(this, server);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        setResizable(true);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        mainNorth = new JPanel(new GridLayout(1, 1));
        JPanel mainBottom = new JPanel(new GridLayout(1, 1));
        loginPassPanel = loginPassPanel();
        disconnectPanel = disconnectPanel();
        mainNorth.add(loginPassPanel);
        mainBottom.add(sendMessagePanel());
        add(mainNorth, BorderLayout.NORTH);
        add(scrollPane);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isConnected()) {
                    disconnect();
                    client.removeFromList();
                }
                dispose();
            }
        });
    }

    /**
     * Creates the disconnection panel.
     *
     * @return The disconnection panel component.
     */
    private Component disconnectPanel() {
        discPanel = new JPanel(new GridLayout(2, 3));
        JButton btnDisc = new JButton("Disconnect");
        loginName = new JLabel("");
        discPanel.add(new JLabel());
        discPanel.add(new JLabel());
        discPanel.add(loginName);
        discPanel.add(new JLabel());
        discPanel.add(new JLabel());
        discPanel.add(btnDisc);
        btnDisc.addActionListener(e -> {
            disconnect();
            client.removeFromList();
        });
        return discPanel;
    }

    /**
     * Creates the login and password input panel.
     * Has a login listener to login in server.
     *
     * @return The login and password input panel component.
     */
    private Component loginPassPanel() {
        panelLogin = new JPanel(new GridLayout(2, 3));
        textFieldIP = new JTextField();
        textFieldIP.setText(DEFAULT_IP);
        textFieldPort = new JTextField();
        textFieldPort.setText(DEFAULT_PORT);
        textFieldLogin = new JTextField();
        textFieldLogin.setText(Server.DEFAULT_NAME + "1");
        passwordField = new JPasswordField();
        passwordField.setText(Server.DEFAULT_PASS);
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> connectToServer());
        panelLogin.add(textFieldIP);
        panelLogin.add(textFieldPort);
        panelLogin.add(new JLabel(""));
        panelLogin.add(textFieldLogin);
        panelLogin.add(passwordField);
        panelLogin.add(btnLogin);
        return panelLogin;
    }

    /**
     * Creates the send message panel.
     *
     * @return The send message panel component.
     */
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
        btnSend.addActionListener(e -> sendMessage());
        panelSendMessage.add(textFieldSend);
        panelSendMessage.add(btnSend);
        return panelSendMessage;
    }

    /**
     * Connects the client to the server.
     */
    private void connectToServer() {
        if (client.isServerWorking()) {
            Account user = new Account();
            user.setIp(textFieldIP.getText());
            user.setPort(textFieldPort.getText());
            user.setName(textFieldLogin.getText());
            user.setPass(passwordField.getText());
            client.setUser(user);
            try {
                if (client.checkUser()) {
                    client.setConnected(true);
                    client.appendToList();
                    client.sendMessageToServer("User " + client.getUserName() + " is connected\n");
                    switchLoginPanel();
                    loginName.setText("You are logged as: " + client.getUserName());
                    try {
                        textArea.setText(client.getHistory());
                    } catch (FileProblemsEx e) {
                        client.sendMessageToServer(client.getUserName() + " can`t load history\n");
                        JOptionPane.showMessageDialog(textArea,
                                "Can`t load history, contact to administrator");

                    }
                    JOptionPane.showMessageDialog(textArea, "You are logged in as: "
                            + user.getName());
                } else {
                    JOptionPane.showMessageDialog(panelLogin,
                            "Login or password do not match");
                }
            } catch (AlreadyLoggedEx e) {
                client.sendMessageToServer(client.getUserName() + " already logged in\n");
                JOptionPane.showMessageDialog(textArea,
                        user.getName() + " is already logged in");
            }
        } else {
            JOptionPane.showMessageDialog(panelLogin, "Server is offline");
        }
    }

    /**
     * Sends a message to chat.
     */
    private void sendMessage() {
        if (client.isServerWorking() && client.isConnected()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            String formattedMessage = String.format(formattedDate + " "
                    + client.getUserName() + ": "
                    + textFieldSend.getText() + "\n");
            try {
                client.writeMessage(formattedMessage);
            } catch (FileProblemsEx e) {
                client.sendMessageToServer(client.getUserName() + " is having problems sending a message");
                JOptionPane.showMessageDialog(textArea,
                        "Access denied, contact to administrator");
            }
            client.sendMessageToServer(formattedMessage);
            textFieldSend.setText("");
        } else {
            JOptionPane.showMessageDialog(panelLogin, "Connection failed");
        }
    }

    /**
     * Switches between the login panel and the disconnection panel.
     */
    private void switchLoginPanel() {
        if (panelLogin.isVisible()) {
            panelLogin.setVisible(false);
            mainNorth.removeAll();
            mainNorth.add(disconnectPanel);
            discPanel.setVisible(true);
        } else {
            discPanel.setVisible(false);
            mainNorth.removeAll();
            mainNorth.add(loginPassPanel);
            panelLogin.setVisible(true);
        }

    }

    /**
     * Prints a message to the client's user interface.
     *
     * @param text The text of the message to be displayed.
     */
    @Override
    public void printMessage(String text) {
        if (client.isConnected()) {
            textArea.append(text);
        }
    }

    /**
     * Disconnects the client from the server and send message.
     */
    @Override
    public void disconnect() {
        client.setConnected(false);
        client.sendMessageToServer(client.getUserName() + " is disconnected\n");
        switchLoginPanel();
    }
}
