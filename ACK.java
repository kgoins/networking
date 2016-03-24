class ACK extends Packet {
    public ACK(byte sn, byte id) {
        super.seqNum = sn;
        super.id = id;
        super.checksum = 0;
    }

    public boolean packetDropped() {
        return (super.seqNum == 2) ? true:false;
    }
}
