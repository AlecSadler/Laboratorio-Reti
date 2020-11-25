import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {
    private int port;

    public Client(int p){
        port = p;
    }

    public void run(){
        SocketAddress socket = new InetSocketAddress(port);
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(socket);
            Scanner scanner = new Scanner(System.in);
            ByteBuffer byteBuffer;
            String daInviare;
            
            while(true) {
                System.out.println("Inserire la stringa da ripetere: ");
                daInviare = scanner.nextLine();
                // ho creato un comando per terminare il programma (stringa "exit")
                if (daInviare.equals("exit")){
                    System.out.println("Client termina");
                    socketChannel.close();
                    break;
                }
                byteBuffer = ByteBuffer.wrap(daInviare.getBytes());
                while (byteBuffer.hasRemaining()) {
                    socketChannel.write(byteBuffer);
                }
                byteBuffer.clear();
                byteBuffer.flip();
                // mi preparo a ricevere la risposta
                byteBuffer = ByteBuffer.allocate(1024);
                boolean end = false;
                String echoed = "";
                while (!end) {
                    byteBuffer.clear();
                    int bytesRead = socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    echoed = echoed + StandardCharsets.UTF_8.decode(byteBuffer).toString();
                    byteBuffer.flip();
                    if (bytesRead < 1024) {
                        end=true;
                    }
                }
                byteBuffer.flip();
                System.out.println(echoed);
            }
        } catch (IOException e) {e.printStackTrace();}
    }
}
