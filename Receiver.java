import java.net.*;
import java.io.*;

class Receiver {
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Receiver(String hostname, int port) throws IOException {
        connection = new Socket(hostname, port);
        System.out.println("Socket created");

        InputStream inFromServer= connection.getInputStream();
        in = new ObjectInputStream(inFromServer);

        OutputStream outToServer = connection.getOutputStream();
        out = new ObjectOutputStream(outToServer);
    }

    public void start() throws Exception {
        MessagePacket msg;

        do { msg = receive(); }
        while(!msg.getData().equals("-1"));

        System.out.println("Kill signal received, terminating");
        close();
    }

    private MessagePacket receive() throws Exception {
        MessagePacket msg = (MessagePacket) in.readObject();
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

        try {receiver.start();}
        catch(Exception e) {e.printStackTrace();}
    }
}
