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
            System.out.println("Recieved message: " + packet);

            if(packet instanceof KillSig)
                stop();

            int i = prng.nextInt(100);
            if(i < 24) {
                ACK drop = new ACK(2);
                rcvToSend.send(drop);
                System.out.println("Dropped packet");
                continue; // Don't wait for ACK
            }
            else if( i > 24 && i < 50) {
                packet.setChecksum(packet.getChecksum() +1);
                sendToRcv.send(packet);
                System.out.println("Corrupted packet");
            }
            else {
                sendToRcv.send(packet);
                System.out.println("Sent packet");
            }

            // Wait for reply and forward to sender
            ACK reply = (ACK) rcvToSend.receive();
            System.out.println("ACK received: " + reply);
            rcvToSend.send(reply);
            System.out.println("ACK forwarded");
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
