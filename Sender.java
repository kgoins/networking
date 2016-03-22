import java.net.*;
import java.io.*;

class Sender {
    Socket connection;

    public Sender(String host, int port) throws IOException {
        connection = new Socket(host, port);
    }

    public void send(String msg) throws IOException {
        OutputStream outToServer = connection.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        System.out.println("Sending message: " + msg);
        out.writeUTF(msg);
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void main(String[] args) throws IOException {
        int port = 1880;
        String hostname = "localhost";

        Sender client = new Sender(hostname, port);
        System.out.println("Client started");

        client.send("Hello World");

        client.close();
    }
}
