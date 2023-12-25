import java.util.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DatagramSocket dataSock = new DatagramSocket(6000);
        byte[] buffer;
        String msg;
        System.out.println("Recived Message : ");
        while (true) {
            buffer = new byte[65555];
            DatagramPacket dataPack = new DatagramPacket(buffer, buffer.length);
            dataSock.receive(dataPack);
            msg = new String(buffer).trim();
            System.out.println(msg);
            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("End Of Message");
                break;
            }
            sc.close();
            dataSock.close();
        }
    }
}
