import java.util.*;

public class RSA {
    public static int gcd(int m, int n) {
        while (n != 0) {
            int r = m % n;
            m = n;
            n = r;
        }
        return m;
    }

    public static int finCoPrime(int phi) {
        int e = 2;
        while (e < phi) {
            if (gcd(e, phi) == 1)
                return e;
            e++;
        }
        return -1;
    }

    public static int findPrivateKey(int e, int phi) {
        int d = 0;
        int k = 1;
        while (true) {
            d = (1 + k * phi) / e;
            if ((d * e) % phi == 1)
                return d;
            k++;
        }
    }

    public static int modPow(int b, int e, int m) {
        int res = 1;
        while (e > 0) {
            if (e % 2 == 1) {
                res = (res * b) % m;
            }
            e = e / 2;
            b = (b * b) % m;
        }
        return res;
    }

    public static int[] rsaEncrypt(String ptext, int e, int n) {
        int[] ctext = new int[ptext.length()];
        for (int i = 0; i < ptext.length(); i++) {
            ctext[i] = modPow((int) ptext.charAt(i), e, n);
        }
        return ctext;
    }

    public static String rsaDecrypt(int[] ctext, int d, int n) {
        StringBuilder ptext = new StringBuilder();
        for (int i : ctext) {
            ptext.append((char) modPow(i, d, n));
        }
        return ptext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p = 17;
        int q = 23;
        int n = p * q;
        int phi = (p - 1) * (q - 1);
        int e = finCoPrime(phi);
        int d = findPrivateKey(e, phi);

        System.out.print("Enter Plain-Text : ");
        String Ptext = sc.nextLine();
        int[] ctext = rsaEncrypt(Ptext, e, n);
        System.out.print("CipherText : ");
        for (int i : ctext) {
            System.out.print(i);
        }
        String decText = rsaDecrypt(ctext, d, n);

        System.out.println("\nDecrypted Text : " + decText);
        sc.close();
    }
}
