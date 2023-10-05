package server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame implements ServerView {
    private static final int POS_X =0;
    private static final int POS_Y =0;
    private static final int WIDTH =400;
    private static final int HEIGHT = 800;

    private final Server server;
    private JButton btnStart, btnStop;
    private JTextArea textArea;

    public ServerGUI(Server server){
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X,POS_Y,WIDTH,HEIGHT);
        setTitle("Chat Server");
        setResizable(true);
        btnStop = new JButton("Stop");
        btnStart = new JButton("Start");
        textArea = new JTextArea();
        textArea.setEditable(false);
//        textArea.setText("Some Text");
        btnStart.addActionListener(e -> {
            if (!server.isServerWorking()) {
                server.setServerWorking(true);
                System.out.println("Server started");
                textArea.setText(server.readFromLog());
            }else {
                JOptionPane.showMessageDialog(btnStart, "Server already started");
                System.out.println("Server already started");
            }
        });
        btnStop.addActionListener(e -> {
            if (server.isServerWorking()) {
                server.setServerWorking(false);
                System.out.println("Server Stopped");
                JOptionPane.showMessageDialog(btnStop, "Server Stopped\nClick <Stop> again to exit");
            } else {
                JOptionPane.showMessageDialog(btnStop, " Exiting");
                System.out.println("Exiting");
                System.exit(0);
            }
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel mainBottom = new JPanel(new GridLayout(1, 2));
        mainBottom.add(btnStart);
        mainBottom.add(btnStop);
        add(scrollPane);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }
}
