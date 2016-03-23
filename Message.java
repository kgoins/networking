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

    public Message(String path) throws IOException {
        filePath = path;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String msgText = reader.readLine();
        message = msgText.split(" ");

        packets = new Packet[message.length];
    }

    public Packet[] getPackets() throws IOException {
        System.out.println(message);
        createPackets();

        return packets;
    }

    private void createPackets() throws IOException {
        byte seqNum = 1;
        System.out.println("message length: " + message.length);

        for (int i = 0; i < message.length; i++) {
            seqNum ^= 1;
            byte id = (byte) (i+1);

            Packet packet = new Packet(seqNum, id, message[i]);
            packets[i] = packet;

            System.out.println(packet);
        }
    }

    // Main
    public static void main(String[] args) {
        try {
            Message msg = new Message("test.txt");
            msg.getPackets();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
