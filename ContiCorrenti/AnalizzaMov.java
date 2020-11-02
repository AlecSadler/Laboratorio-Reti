// Task eseguito dai workers del threadpool

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class AnalizzaMov implements Runnable {
    private JSONArray utente;
    private Contatori counters;

    public AnalizzaMov(JSONArray a, Contatori c){
        utente = a;
        counters = c;
    }

    public void run() {
        // scorro sull'array dei movimenti
        Iterator<JSONObject> iterator = utente.iterator();
        while (iterator.hasNext()) {
            String causale = (String) iterator.next().get("Causale");
            switch(causale) {
                case "Bonifico" :
                    counters.incBonifici();
                    break;
                case "Accredito" :
                    counters.incAccrediti();
                    break;
                case "Bollettino" :
                    counters.incBollettini();
                    break;
                case "F24" :
                    counters.incF24();
                    break;
                case "PagoBancomat" :
                    counters.incBancomat();
                    break;
            }
        }
    }

}
