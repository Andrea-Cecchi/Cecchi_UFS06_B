package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Collectors;


public class CustomFrame extends JFrame {
    private JTable table;
    private Socket socket;
    private JTextArea textArea;
    private final String host = "127.0.0.1";
    private final int port = 1234;



    public CustomFrame(){
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setTitle("Interfaccia json");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnExpensive = new JButton("More expensive");
        JButton btnAll = new JButton("All");
        JButton btnSorted = new JButton("Sorted by name");

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnExpensive);
        buttonPanel.add(btnAll);
        buttonPanel.add(btnSorted);
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        container.add(buttonPanel, BorderLayout.PAGE_START);
        container.add(scrollPane, BorderLayout.CENTER);

        btnExpensive.addActionListener(e -> handleBtnRequest("more_expensive"));
        btnAll.addActionListener(e -> handleBtnRequest("all"));
        btnSorted.addActionListener(e -> handleBtnRequest("all_sorted"));
    }

    void handleBtnRequest(String query){
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    textArea.setText(null);
                    getData(query);
                    return null;
                }
            };
            worker.execute();
        }
    }

    void getData(String s) {
        textArea.setText(null);
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (
                IOException e) {
            System.out.println("impossibile scrivere sul socket");
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("cannot allocate bufferedreader");
        }

        out.println(s);
        out.flush();
        StringBuilder response = new StringBuilder();
        String line;

        boolean continua = true;

        while (continua) {

            try {
                System.out.println(in.readLine());
                line = in.readLine();
                if (line == null || line.isEmpty()) {
                    continua = false;
                } else {
                    response.append(line);
                    response.append("\n");
                    textArea.setText(response.toString());
                }
            } catch (IOException e) {
                System.out.println("impossibile leggere il socket");
            }
        }
    }


}