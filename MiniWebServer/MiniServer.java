import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.StringTokenizer;

// per eseguire: lanciare il programma, aprire un browser e digitare localhost:6789/nomefile.tipo
// per arrestare il servizio al posto del nome file digitare exit, oppure attendere il timeout di 30s

public class MiniServer {
    public static void main (String[] args) {
        try {
            ServerSocket serv = new ServerSocket(6789);
            serv.setSoTimeout(30000);
            Socket sk = null;
            BufferedReader fromClient = null;
            DataOutputStream out = null;
            System.out.println("Server avviato, attendo richieste da browser...");
            while (true) {
                sk = serv.accept();
                fromClient = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                out = new DataOutputStream(new BufferedOutputStream(sk.getOutputStream()));
                String line = fromClient.readLine();
                System.out.println("Richiesta HTTP effettuata: "+line);
                StringTokenizer tok = new StringTokenizer(line);
                // messaggio: get /file.ext HTTP/1.1 mi serve il secondo token
                tok.nextToken();
                String path = tok.nextToken();
                // se voglio arrestare il servizio
                if (path.equals("/exit")){
                    System.out.println("Arresto servizio...");
                    break;
                }
                // ignoro le richieste favicon.ico
                if (path.equals("/favicon.ico")) {
                    sk.close();
                    fromClient.close();
                    out.close();
                    continue;
                }
                String dir = new File(".").getAbsolutePath();  // da modificare altrimenti non funziona fuiri da intellij
                File req = new File(dir + path);
                // se non è un file (es. cartella) termino
                if (!req.isFile()){
                    sk.close();
                    fromClient.close();
                    out.close();
                    System.out.println("Errore nel recupero della risorsa");
                    System.exit(0);
                }
                FileInputStream streamIn = new FileInputStream(req);
                FileChannel chIn = streamIn.getChannel();
                ByteBuffer buf = ByteBuffer.allocate((int) req.length());
                chIn.read(buf);
                buf.flip();

                out.writeBytes("HTTP/1.1 200 OK\r\n\r\n");
                out.write(buf.array());
                out.flush();

                streamIn.close();
                chIn.close();
                sk.close();
                fromClient.close();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());}

    }
}
