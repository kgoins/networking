import java.io.*;
import java.util.Arrays;

// Reads file in path, breaks up into words
//  creates packets out of words
// A message will contain only one message
//   if multiple messages need to be sent, create multiple instances
class Message{
    private String[] message;
    private MessagePacket[] packets;
    private String filePath;

    public Message(String path) throws IOException {
        filePath = path;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String msgText = reader.readLine();
        message = msgText.split(" ");

        packets = null;
    }

    public int getLength() {
        return message.length;
    }

    public MessagePacket[] getPackets() throws IOException {
        if(packets == null)
            createPackets();
        return packets;
    }

    private void createPackets() throws IOException {
        packets = new MessagePacket[message.length];
        byte seqNum = 1;
        System.out.println("message length: " + message.length);

        for (int i = 0; i < message.length; i++) {
           seqNum ^= 1;
           byte id = (byte) (i+1);

           MessagePacket packet = new MessagePacket(seqNum, id, message[i]);
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
