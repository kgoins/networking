import java.net.*;
import java.io.*;

class Network {
    private ServerSocket server;
    private Socket sender, receiver;
    private Pipe sendToRcv, rcvToSend;

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
    }

    public void start() throws IOException {
        initialize();
        String msg = "";

        while(!msg.equals("-1")) {
            System.out.println("Waiting for message");
            msg = sendToRcv.receive();

            System.out.println("Recieved message: " + msg);
            sendToRcv.send(msg);
        }

        System.out.println("Kill signal recieved, terminating");
        stop();
    }

    public void stop() throws IOException {
        System.out.println("Sending kill signal and shutting down");
        String killsig = "-1";
        sendToRcv.send(killsig);
        rcvToSend.send(killsig);

        server.close();
    }

    // Main
    public static void main(String[] args) throws IOException {
        int port = 1880;
        Network skynet = new Network(port);

        skynet.start();
    }
}
