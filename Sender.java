import java.net.*;
import java.io.*;

class Sender {
    private Socket connection;
    private DataOutputStream out;
    private DataInputStream in;
    private String[] messages;

    public Sender(String host, int port) throws IOException {
        connection = new Socket(host, port);

        OutputStream outToServer = connection.getOutputStream();
        out = new DataOutputStream(outToServer);

        InputStream inFromServer= connection.getInputStream();
        in = new DataInputStream(inFromServer);
    }

    private void getMessage() {
        messages = new String[4];
        for (String msg : messages)
            msg = "Hello";
    }


    public void start() throws IOException {
        getMessage();

        String reply = "";
        for (String msg : messages) {
            send(msg);
            // reply = in.readUTF();

            // while(!reply.equals("ACK")) {
            //     System.out.println("ACK not received, resending");
            //     send(msg);
            //     reply = in.readUTF();
            // }

            System.out.println("Msg: " + msg + "sent");
        }

        System.out.println("All messages sent, terminating");
        close();
    }

    private void send(String msg) throws IOException {
        System.out.println("Sending message: " + msg);
        out.writeUTF(msg);
    }

    private void close() throws IOException {
        out.writeUTF("-1");
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
