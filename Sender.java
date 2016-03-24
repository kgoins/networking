import java.net.*;
import java.io.*;

class Sender {
    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private MessagePacket[] packets;

    public Sender(String host, int port) throws IOException {
        connection = new Socket(host, port);

        OutputStream outToServer = connection.getOutputStream();
        out = new ObjectOutputStream(outToServer);

        InputStream inFromServer= connection.getInputStream();
        in = new ObjectInputStream(inFromServer);
    }

    public void send(Message message) throws Exception {
        packets = message.getPackets();

        for(MessagePacket packet : packets) {
            System.out.println("Sending packet: " + packet);
            out.writeObject(packet);

            ACK reply = (ACK) in.readObject();
            System.out.println("ACK received");
            while (reply.packetDropped()) {
                out.writeObject(packet);
                reply = (ACK) in.readObject();
            }

            System.out.println("Packet sent");
        }
    }

    public void close() throws IOException {
        System.out.println("Sending killsig and terminating");
        KillSig killsig = new KillSig();
        out.writeObject(killsig);

        connection.close();
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        String hostname = "localhost";
        Message message = new Message("test.txt");

        Sender sender = new Sender(hostname, port);
        System.out.println("Sender started");

        try {sender.send(message);}
        catch(Exception e) {e.printStackTrace();}

        System.out.println("Closing sender");
        sender.close();
    }
}
