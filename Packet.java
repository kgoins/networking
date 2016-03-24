import java.net.*;
import java.io.*;

abstract class Packet implements Serializable {
    protected byte seqNum, id;
    protected int checksum;
    String data;

    public Packet() {
        data = null;
        seqNum = 0;
        id = 0;
        checksum = 0;
    }

    public String toString() {
        return seqNum + " " + id + " " + checksum + " " + data;
    }

    // Getters
    public String getData() {
        return data;
    }
    public int getSeq() {
        return (int) seqNum;
    }
    public int getID() {
        return (int) id;
    }
    public int getChecksum() {
        return checksum;
    }

}
