class ACK extends Packet {
    public ACK(byte sn, byte id) {
        super.seqNum = sn;
        super.id = id;

        super.data = null;
        super.checksum = 0;
    }
}
