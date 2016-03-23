abstract class Packet {
    protected byte seqNum, id;
    protected int checksum;
    protected String data;

    public String toString() {
        return seqNum + " " + id + " " + checksum + " " + data;
    }
}
