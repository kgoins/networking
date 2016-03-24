import java.net.*;
import java.io.*;

class MessagePacket extends Packet implements Serializable {
    public MessagePacket(byte snum, byte idnum, String contents) {
        super.seqNum = snum;
        super.id = idnum;
        super.data = contents;
        super.checksum = calcChecksum(contents);
    }

    public boolean isCorrupted() {
        return (super.checksum != calcChecksum(super.data)) ? true:false;
    }
    
    public int calcChecksum(String contents) {
        int sum = 0;
        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            sum += (int) c;
        }
        return sum;
    }
}
