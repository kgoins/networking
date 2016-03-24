import java.net.*;
import java.io.*;

class KillSig extends Packet implements Serializable {
    public KillSig() {
        super.seqNum = (byte) -1;
    }
}
