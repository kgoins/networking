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
        while(true)
            msg = receive();
    }

    private MessagePacket receive() throws Exception {
        Packet msg = (Packet) in.readObject();

        if(msg instanceof KillSig)
            close();

        System.out.println("Message received: " + msg);

        int seqNum = msg.getSeq();
        ACK reply = new ACK(seqNum);
        out.writeObject(reply);

        return (MessagePacket) msg;
    }

    private void close() throws IOException {
        System.out.println("Killsig received, terminating");
        connection.close();
        System.exit(0);
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
