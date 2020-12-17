import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

// compile and execute this code and send requests from client.py

public class server {
    public static void main(String[] args) {
        DatagramSocket sk = null;
        try {
            sk = new DatagramSocket(8888);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (sk==null) System.exit(1);

        DatagramPacket rec = null;
        DatagramPacket echo = null;
        byte[] strbytes = null;
        System.out.println("Server is ready to accept requests...");
        while (true) {
            try {
                // ricevo
                byte[] buffer = new byte[1024];
                rec = new DatagramPacket(buffer, buffer.length);
                sk.receive(rec);
                String str = new String(buffer,"UTF-8");
                System.out.println("String from client: " + str);

                // restituisco echo
                strbytes = null;
                strbytes = new byte[str.length()];
                strbytes = str.getBytes(StandardCharsets.UTF_8);
                echo = new DatagramPacket(strbytes,0, strbytes.length, rec.getAddress(), rec.getPort());
                sk.send(echo);

                if (str.contains("exit")){
                    System.out.println("Closing condection...");
                    sk.close();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
