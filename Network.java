import java.net.*;
import java.io.*;

class Network {
  ServerSocket router;
  Socket sender, rcvr;

  public Network(int port) throws IOException {
    router = new ServerSocket(port);
  }

  public void start() throws IOException {
    System.out.println("Skynet Started");
    sender = router.accept();
    rcvr = router.accept();
    System.out.println("Connection Accepted");
  }

  public String rcv() throws IOException {
    InputStream inFromServer= rcvr.getInputStream();
    DataInputStream in = new DataInputStream(inFromServer);

    String msg = in.readUTF();
    System.out.println("Message received: " + msg);

    return msg;
  }

  public void stop() throws IOException {
    sender.close();
    rcvr.close();
  }


  // Main
  public static void main(String[] args) throws IOException {
      int port = 1880;
      Network skynet = new Network(port);
      skynet.start();
      skynet.stop();
  }
}
