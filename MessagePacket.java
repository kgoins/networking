class MessagePacket extends Packet {
    public MessagePacket(byte snum, byte idnum, String contents) {
        seqNum = snum;
        id = idnum;
        data = contents;
        checksum = getChecksum();
    }

    public int getChecksum() {
        int sum = 0;
        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            sum += (int) c;
        }
        return sum;
    }
}
