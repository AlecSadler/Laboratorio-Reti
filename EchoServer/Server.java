import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {
    private int port;

    public Server(int p){
        port = p;
    }

    public void run(){
        Selector selector = null;
        ServerSocket socServer=null;
        ServerSocketChannel chServer=null;
        
        try {
            chServer = ServerSocketChannel.open();
            chServer.configureBlocking(false); // imposto come non bloccante
            socServer = chServer.socket();
            InetSocketAddress addr = new InetSocketAddress(port);
            socServer.bind(addr);
            selector = Selector.open();
            chServer.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {e.printStackTrace();}
        
        System.out.println("Server avviato, attesa richieste...");
        while (true) {
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Server termina");
                try {
                    socServer.close();
                    chServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            //keySearch cerca i canali pronti
            Set<SelectionKey> keySearch = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySearch.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) { // chiave accettazione pronta
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Connection OK");
                        client.configureBlocking(false);
                        SelectionKey keyCli = client.register(selector, SelectionKey.OP_READ, null);
                    }
                    else if (key.isWritable()) { // chiave scrittura pronta
                        SocketChannel client = (SocketChannel) key.channel();
                        String toWrite = (String)key.attachment();
                        ByteBuffer bRead = ByteBuffer.wrap(toWrite.getBytes()); // leggo la stringa
                        int bWrite = client.write(bRead);
                        if (bWrite == toWrite.length()) {
                            key.attach(null);
                            key.interestOps(SelectionKey.OP_READ);
                        }
                        else if (bWrite == -1) {
                            key.cancel();
                            key.channel().close();
                        }
                        else {
                            bRead.flip();
                            key.attach(StandardCharsets.UTF_8.decode(bRead).toString());
                        }
                    }
                    else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        String toEcho = (String) key.attachment();
                        if (toEcho == null) toEcho = "(echoed by Server) ";
                        ByteBuffer input = ByteBuffer.allocate(1024);
                        input.clear();
                        int byRead = client.read(input);
                        if (byRead == 1024){
                            input.flip();
                            toEcho = toEcho + StandardCharsets.UTF_8.decode(input).toString();;
                            key.attach(toEcho);
                        }
                        else if (byRead < 1024) {
                            input.flip();
                            toEcho = toEcho + StandardCharsets.UTF_8.decode(input).toString();
                            key.attach(toEcho);
                            key.interestOps(SelectionKey.OP_WRITE);
                        }
                        else if (byRead == -1) {
                            key.cancel();
                            key.channel().close();
                        }
                    }
                } catch (IOException e) {
                    key.cancel();  
                    try {
                        key.channel().close();
                    } catch (IOException e1) {e1.printStackTrace();}
                }
            }
        }
    }
}
