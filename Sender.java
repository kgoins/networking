import java.net.*;
import java.io.*;

class Sender {
    private Socket connection;
    private DataOutputStream out;
    private DataInputStream in;
    private String[] message;

    public Sender(String host, int port) throws IOException {
        connection = new Socket(host, port);

        OutputStream outToServer = connection.getOutputStream();
        out = new DataOutputStream(outToServer);

        InputStream inFromServer= connection.getInputStream();
        in = new DataInputStream(inFromServer);
    }

    public void getMessageFromFile(String path) {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        messages = new String[4];
        for (String msg : messages)
            msg = "Hello";
    }


    public void start() throws IOException {
        String reply = "";
        for (String msg : messages) {
            send(msg);

        }

        System.out.println("All messages sent, terminating");
        close();
    }

    private void send(String msg) throws IOException {
        System.out.println("Sending message: " + msg);
        out.writeUTF(msg);

            // reply = in.readUTF();
            // while(!reply.equals("ACK")) {
            //     System.out.println("ACK not received, resending");
            //     send(msg);
            //     reply = in.readUTF();
            // }
            System.out.println("Msg: " + msg + "sent");

    }

    public void close() throws IOException {
        out.writeUTF("-1");
        connection.close();
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        String hostname = "localhost";

        Sender sender = new Sender(hostname, port);
        System.out.println("Sender started");

        sender.getMessageFromFile("message.txt");
        sender.start();

        System.out.println("Closing sender");
        sender.close();
    }
}
