import java.net.*;
import java.io.*;

class Receiver {
    private Socket connection;
    private DataInputStream in;

    public Receiver(String hostname, int port) throws IOException {
        connection = new Socket(hostname, port);

        InputStream inFromServer= connection.getInputStream();
        in = new DataInputStream(inFromServer);
    }

    public void start() throws IOException {
        String msg = "";
        while(!msg.equals("-1")) {
            msg = receive();
        }

        System.out.println("Kill signal received, terminating");
        close();
    }

    private String receive() throws IOException {
        String msg = in.readUTF();
        System.out.println("Message received: " + msg);

        return msg;
    }

    private void close() throws IOException {
        connection.close();
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        String hostname = "localhost";

        Receiver receiver = new Receiver(hostname, port);
        System.out.println("Receiver started");

        receiver.start();
    }
}
