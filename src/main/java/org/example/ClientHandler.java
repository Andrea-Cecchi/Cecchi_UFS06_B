package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {

    private static Socket clientSocket = null;

    private PrintWriter out = null;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        InetAddress address = clientSocket.getInetAddress();
        int port = clientSocket.getPort();
        System.out.println("connected " + address + " on port: " + port);
    }

    void handle(){

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            readLoop(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readLoop(BufferedReader in, PrintWriter out) {
        String s = "";

        try{
            while((s = in.readLine()) != null){
                switch(s.toLowerCase()){
                    case("more_expensive"):
                        out.println(HotelBroker.getInstance().getMoreExpensiveTCP());
                        break;
                    case("all"):
                        out.println(HotelBroker.getInstance().toJson());
                        break;
                    case("all_sorted"):
                        out.println(HotelBroker.getInstance().toJsonSortedTCP());
                        break;
                    default:
                        System.out.println("Comando non trovato!");
                        out.println("Comando non trovato!, prova con: more_expensive, all, all_sorted");
                        break;
                }
            }
        }catch(SocketException se){
            InetAddress address = clientSocket.getInetAddress();
            int port = clientSocket.getPort();ClientManager.getInstance().remove(this);
            System.out.println("disconnected " + address + " on port: " + port + " now we have " + ClientManager.getInstance().numberOfClients() + " clients");

        }catch(IOException e){
            System.out.println("Errore nel comando");
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Impossibile chiudere il socket");
        }
    }

    void write(String s){
        out.println(s);
        out.flush();
    }

    @Override
    public void run() {
        handle();
    }
}
