import java.io.IOException;

public class Main {
    public static void main (String[] args){
        JsonGenerator bank = new JsonGenerator(7, 36); // testo con 5 clienti con 36 operazioni ciascuno
        try {
            bank.generaJson();
        } catch (IOException e){e.printStackTrace();}
        Contatori riepilogo = new Contatori();

        Workers thPool = new Workers(5);

        Reader rd = new Reader(riepilogo, thPool);
        Thread t1 = new Thread(rd);
        t1.start();
    }
}
