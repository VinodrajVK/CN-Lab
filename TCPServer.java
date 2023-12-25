import java.util.*;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serSoc = new ServerSocket(5000);
        System.out.println("Server Established.");
        Socket socket = serSoc.accept();
        System.out.println("Connection Established. Waiting For Client..");

        InputStream istream = socket.getInputStream();
        Scanner sc = new Scanner(new InputStreamReader(istream));
        String fname = sc.nextLine();

        OutputStream ostream = socket.getOutputStream();
        PrintWriter pWrite = new PrintWriter(ostream, true);

        try {
            Scanner filescan = new Scanner(new FileReader(fname));
            while (filescan.hasNextLine()) {
                pWrite.println(filescan.nextLine());
            }
            filescan.close();
        } catch (FileNotFoundException e) {
            pWrite.println("File not found");
        } finally {
            System.out.println("Closing Connection");
            sc.close();
            socket.close();
            serSoc.close();
        }
    }
}
