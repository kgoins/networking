import java.net.*;
import java.io.*;

class ACK extends Packet implements Serializable {
    public ACK(int sn) {
        super.seqNum = (byte) sn;
    }

    public boolean packetDropped() {
        return (super.seqNum == 2) ? true:false;
    }
}
