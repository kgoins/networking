import java.net.*;
import java.io.*;

class Pipe {
    private Socket sender, receiver;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Pipe(Socket from, Socket to) throws IOException {
        InputStream input = from.getInputStream();
        OutputStream output = to.getOutputStream();

        in = new ObjectInputStream(input);
        out = new ObjectOutputStream(output);
    }

    public void send(Packet packet) throws IOException {
        out.writeObject(packet);
    }

    public Packet receive() throws Exception {
        Packet msg = (Packet) in.readObject();
        System.out.println("Killsig? " + (msg instanceof KillSig));
        return msg;
    }
}
