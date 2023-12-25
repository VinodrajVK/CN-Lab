import java.util.*;

public class TokenBucket {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Bucket Capacity : ");
        int bsize = sc.nextInt();
        int tokens = 0;
        int tokenrate = 3;
        System.out.println("Enter the Number of Requests : ");
        int n = sc.nextInt();
        int[] requests = new int[n];
        System.out.println("Enter the number of packets in each requests");
        for (int i = 0; i < n; i++) {
            requests[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            tokens = Integer.min((tokens + tokenrate), bsize);
            System.out.println("Tokens Present : " + tokens);
            if (requests[i] <= tokens) {
                System.out.println("Number of Packets : " + requests[i]);
                System.out.println("Packets Accepted");
                tokens -= requests[i];
                System.out.println("Tokens remaining : " + tokens);
            } else {
                System.out.println("Number of Packets : " + requests[i]);
                System.out.println("Packets Dropped (Insufficient Tokens)");
                System.out.println("Tokens remaining : " + tokens);
            }
        }
        sc.close();
    }
}
