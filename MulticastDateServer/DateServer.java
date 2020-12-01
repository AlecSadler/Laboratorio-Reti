import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// testato su indirizzo 224.0.0.0
public class DateServer {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.printf("Inserire l'indirizzo multicast del datagroup:");
        String indirizzo = in.nextLine();

        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(indirizzo);
        } catch (UnknownHostException e){e.printStackTrace();};
        // controllo se è un indirizzo multicast (classe D)
        if (!addr.isMulticastAddress()){
            System.out.print("L'indirizzo indicato non è un indirizzo multicast!");
            System.exit(1);
        }
        DatagramSocket servSoc = null;
        try {
            servSoc = new DatagramSocket();
        } catch (SocketException e){e.printStackTrace();}
        LocalDateTime dt;
        DateTimeFormatter dtTostring = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        while (true){
            dt = LocalDateTime.now();
            String toCli = dt.format(dtTostring);
            DatagramPacket packet = new DatagramPacket(toCli.getBytes(), toCli.length(), addr, 8888);
            try {
                servSoc.send(packet);
            } catch (IOException e) {e.printStackTrace();}

            // setto un intervallo di 5s tra i pacchetti
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
