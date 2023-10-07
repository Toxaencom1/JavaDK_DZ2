package server;

import exceptions.FileProblemsEx;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame implements ServerView {
    private static final int POS_X = 0;
    private static final int POS_Y = 0;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;

    private final Server server;
    private JButton btnStart, btnStop;
    private JTextArea messageLog, tempLogArea;

    /**
     * Constructs a new `ServerGUI` with the specified file storage component.
     *
     * @param storage The file storage component for logging messages.
     */
    public ServerGUI(FileJob storage) {
        this.server = new Server(this, storage);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Server");
        setResizable(true);
        btnStop = new JButton("Stop");
        btnStart = new JButton("Start");
        messageLog = new JTextArea();
        tempLogArea = new JTextArea();
        messageLog.setEditable(false);
        btnStart.addActionListener(e -> {
            if (!server.isServerWorking()) {
                server.setServerWorking(true);
                try {
                    messageLog.setText(server.readFromLog());
                } catch (FileProblemsEx ex) {
                    printMessage("Can`t load history, contact to administrator\n");
                }
                tempLogArea.append("Server started\n");
            } else {
                tempLogArea.append("Server already started\n");
                JOptionPane.showMessageDialog(btnStart, "Server already started");
            }
        });
        btnStop.addActionListener(e -> {
            if (server.isServerWorking()) {
                server.setServerWorking(false);
                server.disconnectAll();
                tempLogArea.append("Server Stopped\n");
                JOptionPane.showMessageDialog(btnStop, "Server Stopped\nClick <Stop> again to exit");
            } else {
                tempLogArea.append("Exiting\n");
                JOptionPane.showMessageDialog(btnStop, " Exiting");
                System.exit(0);
            }
        });
        JScrollPane scrollPane = new JScrollPane(tempLogArea);
        JPanel mainBottom = new JPanel(new GridLayout(1, 2));
        mainBottom.add(btnStart);
        mainBottom.add(btnStop);
        add(scrollPane);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Prints a message to the user interface.
     *
     * @param message The text of the message to be displayed.
     */
    @Override
    public void printMessage(String message) {
        tempLogArea.append(message);
    }

    /**
     * Retrieves the associated server instance.
     *
     * @return The server instance associated with this view.
     */
    @Override
    public Server getServer() {
        return server;
    }
}
