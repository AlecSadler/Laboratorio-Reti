import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JsonGenerator {
    private int noClienti;
    private int noMovim;
    private String path;

    public JsonGenerator(int c, int m){
        noClienti = c;
        noMovim = m;
        path = "./src/myBank.json";
    }

    // serve per generare la causale
    private String generaCaus(int n){
        switch(n) {
            case 0 :
                return "Bonifico";
            case 1 :
                return "Accredito";
            case 2 :
                return "Bollettino";
            case 3 :
                return "F24";
            case 4 :
                return "PagoBancomat";
        }
        return null;
    }

    // genera un movimento totalmente casuale
    private JSONObject generaMov(){
        JSONObject op = new JSONObject();
        int y = (int) (Math.random()*(2)) + 2019;
        int m = (int) (Math.random()*(11)) + 1;
        int d = (int) (Math.random()*(30)) + 1;
        op.put("Data", y+"-"+m+"-"+d);
        op.put("Causale", generaCaus((int) (Math.random()*5))); // genero un tipo casuale di operazione
        return op;
    }

    // genera la lista movimenti
    public JSONArray generaEC(){
        JSONArray listaMov = new JSONArray();
        for (int i=0; i<noMovim; ++i){
            listaMov.add(generaMov());
        }
        return listaMov;
    }

    // genera il cliente
    public JSONObject generaConto(int id){
        JSONObject conto = new JSONObject();
        conto.put("id", "ID_" + id);
        conto.put("movimenti", generaEC());
        return conto;
    }

    // crea il file
    public void generaJson() throws IOException {
        JSONArray bank = new JSONArray();
        for (int i = 0; i<noClienti; ++i) {
            bank.add(generaConto(i));
        }
        ByteBuffer buf = ByteBuffer.wrap(bank.toJSONString().getBytes("UTF-8"));
        try {
            Files.createFile(Paths.get(path)); //creo il json
        } catch(IOException e) {e.printStackTrace();}
        FileChannel chOut = FileChannel.open(Paths.get(path), StandardOpenOption.WRITE);
        while(buf.hasRemaining()) {
            chOut.write(buf);
        }
        chOut.close();
        System.out.println("File JSon creato correttamente.");
    }


}
