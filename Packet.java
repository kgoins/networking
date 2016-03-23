class Packet {
    private byte seqNum, id;
    private int checksum;
    private String data;

    public Packet(byte snum, byte idnum, String contents) {
        seqNum = snum;
        id = idnum;
        data = contents;
        checksum = calcChecksum();
    }

    public int calcChecksum() {
        int sum = 0;
        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            sum += Character.getNumericValue(c);
        }
        return sum;
    }
}

// class ACK {
//     byte seqNum, checksum;
//     public ACK(int num) {
//        seqNum = (byte) num;
//        checksum = 0;
//     }

//     public int seqNum() {
//         return (int) seqNum;
//     }
// }
