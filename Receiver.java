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
        int expectedSeqNum = 0;

        while(true) {
            msg = receive(expectedSeqNum);
            expectedSeqNum ^= 1;
        }
    }

    private void close() throws IOException {
        System.out.println("Killsig received, terminating");
        connection.close();
        System.exit(0);
    }

    private MessagePacket receive(int seqNum) throws Exception {
        Packet packet = (Packet) in.readObject();
        System.out.println("Message received: " + packet);

        if(packet instanceof KillSig)
            close();

        MessagePacket msg = (MessagePacket) packet;
        ACK reply;

        if(msg.isCorrupted()) {
            System.out.println("Packet corrupted :-(");
            reply = new ACK(2);
        }
        else if(msg.getSeq() != seqNum)
            reply = new ACK(2);
        else
            reply = new ACK(msg.getSeq());

        out.writeObject(reply);
        System.out.println("Reply sent");

        return msg;
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
