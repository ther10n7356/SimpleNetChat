package net.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private final Socket socket;
    private Scanner in;
    private PrintStream out;
    private final ChatServer server;

    public Client(Socket socket, ChatServer server){
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }

    void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Welcome to chat!");
            out.print("Enter name: ");
            String name = in.nextLine();
            String input = enterMessage();
            while (!input.equals("bye")) {
                server.sendAll(this, name + ": " + input);
                input = enterMessage();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String enterMessage() {
        return in.nextLine();
    }
}
