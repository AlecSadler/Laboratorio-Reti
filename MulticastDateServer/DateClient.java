import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

// testato su indirizzo 224.0.0.0
public class DateClient {
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
        MulticastSocket cliSoc = null;
        try {
            cliSoc = new MulticastSocket(8888);
            cliSoc.joinGroup(addr);
        } catch (IOException e) {e.printStackTrace();}
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        for (int i=0; i<10; ++i){
            try {
                cliSoc.receive(packet);
            } catch (IOException e) {e.printStackTrace();}
            String dt;
            dt = new String(packet.getData(), 0, packet.getLength());
            System.out.println(dt);
        }
        cliSoc.close();
    }
}
