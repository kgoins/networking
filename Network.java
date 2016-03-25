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
        Packet packet = sendToRcv.receive();

        while(!(packet instanceof KillSig)) {
            System.out.println("Recieved message: " + packet);

            sendToRcv.send(packet);

            // Wait for ACK and forward
            Packet reply = rcvToSend.receive();
            System.out.println(reply);
            rcvToSend.send(reply);

            packet = sendToRcv.receive();
        }

        System.out.println("Kill signal recieved, terminating");
        stop();
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
