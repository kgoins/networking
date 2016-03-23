import java.io.*;
import java.util.ArrayList;

// Reads file in path, breaks up into words
//  creates packets out of words
// A message will contain only one message
//   if multiple messages need to be sent, create multiple instances
class Message{
    private String[] message;
    private Packet[] packets;
    private String filePath;

    public Message(String path) {
        filePath = path;
    }

    public Packet[] getPackets() throws IOException {
        message = getMsgFromFile();
        parseMessage();

        packets = new Packet[message.length];
        return packets;
    }

    private String[] getMsgFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String msgText = reader.readLine();
        return msgText.split(" ");
    }

    private void parseMessage() throws IOException {
        byte seqNum = 0;
        for (int i = 0; i < message.length; i++) {
            seqNum ^= 1;
            byte id = (byte) (i+1);
            Packet packet = new Packet(seqNum, id, message[i]);
            packets[i] = packet;
        }
    }

    // Main
    public static void main(String[] args) {
        Message msg = new Message("test.txt");
    }

}
