import java.io.*;
import java.util.ArrayList;

// Reads file in path, breaks up into words
//  creates packets out of words
class Message{
    ArrayList<String> messages;
    ArrayList<Packet> packets;
    String filePath;

    public Message() {
    }

    private void readMessages(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
    }

    public Packet[] parse(String path) throws IOException {
        readMessages(path);

        Packet[] packetsOut = new Packet[packets.size()];
        packetsOut = packets.toArray(packetsOut);
        return packetsOut;
    }

}
