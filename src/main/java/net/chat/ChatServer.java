package net.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private List<Client> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(Client client, String message) {
        clients.stream()
                .filter(cl-> cl != client)
                .forEach(cl -> cl.receive(message));
    }

    public void run() {
        while (true) {
            System.out.println("Waiting...");

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}
