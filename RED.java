import java.util.*;

public class RED {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        float minProb = 0.3F;
        float maxProb = 0.7F;
        float dropProb = minProb;
        System.out.print("Enter the QueueSize : ");
        int queueSize = sc.nextInt();
        System.out.print("Enter the No of Packets : ");
        int numPackets = sc.nextInt();
        int queuLen = 0;
        for (int i = 1; i <= numPackets; i++) {
            if (queuLen >= queueSize) {
                System.out.println("Packet-" + i + " Dropped (Queue Full)");
            } else if (rand.nextFloat() < dropProb) {
                System.out.println("Packet-" + i + " Dropped (Random)");
                dropProb += (maxProb - minProb) / (numPackets - 1);
            } else {
                System.out.println("Packet Accepted");
                queuLen++;
                dropProb = minProb;
            }
        }
        sc.close();
    }
}
