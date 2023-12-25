import java.util.*;

public class CRC {
    String calCRC(String data, String poly, boolean errCheck) {
        StringBuilder rem = new StringBuilder(data);
        if (!errCheck) {
            for (int i = 0; i < poly.length() - 1; i++)
                rem.append("0");
        }
        for (int i = 0; i < rem.length() - poly.length() + 1; i++) {
            if (rem.charAt(i) == '1') {
                for (int j = 0; j <= poly.length() - 1; j++)
                    rem.setCharAt(i + j, rem.charAt(i + j) == poly.charAt(j) ? '0' : '1');
            }
        }
        return rem.substring(rem.length() - poly.length() + 1);
    }

    public static void main(String[] args) {
        CRC ob = new CRC();
        Scanner sc = new Scanner(System.in);
        String poly = "10000100010001010";
        System.out.print("Enter the Data : ");
        String data = sc.nextLine();
        String rem = ob.calCRC(data, poly, false);
        String codeWord = data + rem;
        System.out.println("Code Word : " + codeWord);

        System.out.print("Enter recieved Code Word : ");
        String recData = sc.nextLine();
        String recRem = ob.calCRC(recData, poly, true);

        if (Integer.parseInt(recRem, 2) == 0) {
            System.out.println("No Error");
        } else
            System.out.println("There was an error");
        sc.close();
    }
}
