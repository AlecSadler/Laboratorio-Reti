// FIRERA SALVO - MAT.578018 A

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Ufficio {
    private ThreadPoolExecutor sportelli;


    public Ufficio(){
        this.sportelli = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    }

    public void servi(Cliente c){
        sportelli.execute(c);

    }

    public void close(){
        sportelli.shutdown();
    }

    boolean isClosed(){
        return sportelli.isTerminated();
    }

}
