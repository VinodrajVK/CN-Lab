import java.util.*;
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 5000);
        System.out.print("Enter Filename : ");
        Scanner sc = new Scanner(System.in);
        String fname = sc.nextLine();
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pWrite = new PrintWriter(ostream, true);
        pWrite.println(fname);

        InputStream istream = sock.getInputStream();
        Scanner filescan = new Scanner(new InputStreamReader(istream));
        while (filescan.hasNextLine()) {
            System.out.println(filescan.nextLine());
        }
        filescan.close();
        sc.close();
        sock.close();
        pWrite.close();
    }
}
