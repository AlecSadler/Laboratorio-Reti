// FIRERA SALVO MAT.578018 A

import java.util.concurrent.atomic.AtomicInteger;

public class Prof implements Runnable {
    private AtomicInteger accessi;
    private Laboratorio lab = null;
    private int id;

    public Prof(Laboratorio l, int id){
        this.id = id;
        this.lab= l;
        int k =(int)(Math.random()*(7-1))+1;
        this.accessi = new AtomicInteger();
        this.accessi.set(k);
    }

    public void run(){
        long permanenza = 2500;
        System.out.println("Prof "+this.id+" chiede "+this.accessi+" accessi");
        lab.entraProf(permanenza, this.id, this.accessi);
    }
}
