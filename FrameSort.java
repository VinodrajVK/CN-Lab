import java.util.Scanner;

class Frame {
    int number;
    String data;
}

public class FrameSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of frames: ");
        int n = sc.nextInt();
        Frame[] frames = new Frame[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Frame();
            System.out.print("Enter the frame number: ");
            frames[i].number = sc.nextInt();
            System.out.print("Enter the frame data: ");
            frames[i].data = sc.next();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (frames[j].number > frames[j + 1].number) {
                    Frame temp = frames[j];
                    frames[j] = frames[j + 1];
                    frames[j + 1] = temp;
                }
            }
        }
        System.out.println("Sorted Frames Are:");
        for (int i = 0; i < n; i++)
            System.out.print(frames[i].data + " ");
        sc.close();
    }
}