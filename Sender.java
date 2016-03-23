import java.net.*;
import java.io.*;

class Sender {
    private Socket connection;
    private DataOutputStream out;

    public Sender(String host, int port) throws IOException {
        connection = new Socket(host, port);

        OutputStream outToServer = connection.getOutputStream();
        out = new DataOutputStream(outToServer);
    }

    // private void run() {
    //     String msg;
    // }

    public void send(String msg) throws IOException {
        System.out.println("Sending message: " + msg);
        out.writeUTF(msg);
    }

    public void close() throws IOException {
        connection.close();
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        String hostname = "localhost";

        Sender sender = new Sender(hostname, port);
        System.out.println("Sender started");

        sender.send("Hello World");
        sender.send("Hello World");
        sender.send("Hello World");
        sender.send("Hello World");
        sender.send("-1");

        System.out.println("Closing sender");
        sender.close();
    }
}
