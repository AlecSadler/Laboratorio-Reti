import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class Reader implements Runnable{
    private JSONParser pars;
    private Workers pool;
    private Contatori counters;

    public Reader(Contatori c, Workers pl) {
        pars = new JSONParser();
        counters = c;
        pool = pl;
    }

    public String readFile() throws IOException {
        FileChannel chInput = FileChannel.open(Paths.get("src/myBank.json"), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024);
        boolean exit = false;
        String tmp = "";
        while (!exit) {
            int cursore = chInput.read(buffer);
            if (cursore == -1) {
                exit = true;
            }
            buffer.flip();
            while (buffer.hasRemaining()) {
                tmp = tmp + StandardCharsets.UTF_8.decode(buffer).toString();
            }
            buffer.flip();
        }
        chInput.close();
        return tmp;
    }

    public void run() {
        try {
            JSONArray parsedArray = (JSONArray) pars.parse(readFile());
            Iterator<JSONObject> iterator = parsedArray.iterator();
            
            // scorre sui conti correnti e ogni conto lo passo a un worker del pool
            while (iterator.hasNext()) {
                AnalizzaMov an = new AnalizzaMov((JSONArray)iterator.next().get("movimenti"), counters);
                pool.exec(an);
            }
            pool.termina();
            while(!pool.finish()) {
                Thread.sleep(800);
            }
            counters.printStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
