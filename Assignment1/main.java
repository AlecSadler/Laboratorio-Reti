import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) {
        double precision;
        int timeout;
        Scanner read = new Scanner(System.in);
        precision = read.nextDouble();                 // 0.0001
        timeout = read.nextInt();                      //500
        piGreco pi = new piGreco(precision);
        Thread th = new Thread(pi);
        th.start();
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        th.interrupt();
    }
}
