// FIRERA SALVO - 578018 A

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PingServer {
    public static void main(String[] args){
        
        DatagramSocket servSoc = null;
        try {
            servSoc= new DatagramSocket(8888);
        } catch (SocketException e) {e.printStackTrace();}
        
        byte[] buffRec = new byte[1024];
        DatagramPacket packRec = new DatagramPacket(buffRec,buffRec.length);
        
        while (true) {
            try {
                int aux;
                servSoc.receive(packRec);
                String fromCli;
                fromCli = new String(packRec.getData(), 0, packRec.getLength());
                System.out.println(fromCli);
                aux = (int) (Math.random() * 30);
                if (aux % 6 == 0) {    // funzione per decidere se devo accettare o perdere il pacchetto
                    System.out.println("Request timeout"); // simulo la perdita
                }
                else {
                    long ritardo = (long) (Math.random() * 400); // simulo un ritardo
                    System.out.println("Delay: " + ritardo + " ms");
                    Thread.sleep(ritardo);
                    byte[] arr = fromCli.getBytes(); // rimando lo stesso pacchetto
                    DatagramPacket retPack = new DatagramPacket(arr, arr.length, packRec.getAddress(), packRec.getPort());

                    servSoc.send(retPack);
                }
            } catch (Exception e){e.printStackTrace();}
        }
    }
}
