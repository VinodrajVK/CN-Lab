import java.util.*;

public class LeakyBucket {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Bucket Size : ");
        int bsize = sc.nextInt();
        System.out.print("Enter the Number of Packets : ");
        int numPackets = sc.nextInt();
        System.out.print("Enter the Output Packet Size : ");
        int outsize = sc.nextInt();
        int filledbuffer = 0;
        int sizeleft = bsize - filledbuffer;
        for (int i = 1; i <= numPackets; i++) {
            System.out.println("Packet-" + i);
            System.out.print("Enter the Packet Size : ");
            int ipsize = sc.nextInt();
            sizeleft = bsize - filledbuffer;
            if (ipsize <= sizeleft) {
                System.out.println("Packet-" + i + " filled into bucket");
                filledbuffer += ipsize;
            } else {
                System.out.println("Packet-" + i + " dropped.");
            }
            System.out.println("Filled Buffer Size : " + filledbuffer);
            filledbuffer -= outsize;
        }
        sc.close();
    }
}
