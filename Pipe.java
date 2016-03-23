import java.net.*;
import java.io.*;

class Pipe {
    private Socket sender, receiver;
    private DataInputStream in;
    private DataOutputStream out;

    public Pipe(Socket from, Socket to) throws IOException {
        InputStream input = from.getInputStream();
        OutputStream output = to.getOutputStream();

        in = new DataInputStream(input);
        out = new DataOutputStream(output);
    }

    public void send(String msg) throws IOException {
        out.writeUTF(msg);
    }

    public String receive() throws IOException {
        return in.readUTF();
    }
}
