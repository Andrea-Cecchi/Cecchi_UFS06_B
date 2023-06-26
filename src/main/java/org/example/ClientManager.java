package org.example;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    List<ClientHandler> clientList = new ArrayList<>();

    private static ClientManager INSTANCE;


    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClientManager();
        }

        return INSTANCE;
    }

    void add(ClientHandler client){
        clientList.add(client);
    }

    void remove(ClientHandler client){
        clientList.remove(client);
    }

    int numberOfClients() {
        return clientList.size();
    }
}
