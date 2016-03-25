import java.net.*;
import java.io.*;
import java.util.Random;

class Network {
    private ServerSocket server;
    private Socket sender, receiver;
    private Pipe sendToRcv, rcvToSend;
    private Random prng;

    public Network(int port) throws IOException {
        server = new ServerSocket(port);
    }

    private void initialize() throws IOException {
        System.out.println("Skynet Started");

        receiver = server.accept();
        System.out.println("Receiver Connected");

        sender = server.accept();
        System.out.println("Sender Connected");

        sendToRcv = new Pipe(sender, receiver);
        rcvToSend = new Pipe(receiver, sender);

        prng = new Random();
    }

    public void start() throws Exception {
        initialize();
        System.out.println("Waiting for message");

        while(true) {
            Packet packet = sendToRcv.receive();
            System.out.println();
            System.out.println("Recieved message: " + packet);

            if(packet instanceof KillSig)
                stop();

            int i = prng.nextInt(100);
            System.out.println("i val: " + i);

            // Decide which action to take
            if(i < 24) {
                // Drop packet
                System.out.println("Dropping packet");

                ACK drop = new ACK(2);
                rcvToSend.send(drop);

                System.out.println("Dropped packet");
                continue; // Don't wait for ACK
            }
            else if( i > 24 && i < 50) {
                // Corrupt packet
                System.out.println("Corrupting packet");

                int csum = packet.getChecksum() +1;
                byte seq = (byte) packet.getSeq();
                byte id = (byte) packet.getID();
                String data = packet.getData();

                MessagePacket badPacket = new MessagePacket(seq, id, data);
                badPacket.setChecksum(csum);
                System.out.println("Bad packet: " + badPacket);

                sendToRcv.send(badPacket);
                System.out.println("Corrupted packet sent");

                ACK reply = (ACK) rcvToSend.receive();
                System.out.println("ACK received: " + reply);

                rcvToSend.send(reply);
                System.out.println("ACK forwarded");
            }
            else {
                // Send packet normally
                System.out.println("Sending packet");

                sendToRcv.send(packet);
                System.out.println("Sent packet");

                ACK reply = (ACK) rcvToSend.receive();
                System.out.println("ACK received: " + reply);

                rcvToSend.send(reply);
                System.out.println("ACK forwarded");
            }
        }
    }

    public void stop() throws IOException {
        System.out.println("Broadcasting kill signal and shutting down");
        KillSig killsig = new KillSig();
        sendToRcv.send(killsig);

        server.close();
        System.exit(0);
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        Network skynet = new Network(port);

        try {skynet.start();}
        catch(Exception e) {e.printStackTrace();}
    }
}
