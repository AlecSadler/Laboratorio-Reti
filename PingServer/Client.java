// FIRERA SALVO - 578018 A

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args){
        int nSent = 0, nRec = 0;
        long rttMin= 99999999, rttMax = 0, rttAvg = 0;
        DatagramSocket cliSoc = null;
        DatagramPacket cliPac = null;
        try {
            cliSoc = new DatagramSocket();
            System.out.println("Client attivo su porta: "+cliSoc.getLocalPort());
            cliSoc.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
            cliSoc.close();
            return;
        }
        try {
            for (int i=0; i<10; ++i) {
                long start = System.currentTimeMillis();
                String ping = "Ping n." + i + " - content: " +start;
                byte[] byteArr = ping.getBytes();
                try {
                    cliPac = new DatagramPacket(byteArr, byteArr.length, InetAddress.getByName("Localhost"), 8888);
                } catch (UnknownHostException e) {
                    cliSoc.close();
                    e.printStackTrace();
                }
                cliSoc.send(cliPac);
                nSent++;

                try {
                    cliSoc.receive(cliPac);
                } catch (IOException e) {
                    System.out.println("Packet n." + i + " Timeout response.");
                    continue;
                }
                nRec++;
                String resp = new String(cliPac.getData(), 0, cliPac.getLength());
                long ritardo = System.currentTimeMillis() - start;
                if (ritardo > rttMax) {
                    rttMax = ritardo;
                    rttAvg = rttAvg + ritardo;
                } else if (ritardo < rttMin) {
                    rttMin = ritardo;
                    rttAvg = rttAvg + ritardo;
                }
            }
        } catch (IOException e ){cliSoc.close(); return;}
        cliSoc.close();
        // stampa del report
        System.out.println("\n### PING RESULTS ###");
        System.out.println("Packets sent/received: " + nSent + " / "+nRec);
        System.out.println("Packets lost: " + (100 - (nRec*100)/nSent) + " %");
        System.out.println("RTT min/max/avg: "+ rttMin + " ms / " + rttMax + " ms / " + rttAvg + " ms");
    }
}
