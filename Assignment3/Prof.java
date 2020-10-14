// FIRERA SALVO MAT.578018 A

public class Prof implements Runnable {
    private int accessi;
    private Laboratorio lab = null;
    private int id;

    public Prof(Laboratorio l, int id){
        this.id = id;
        this.lab= l;
        this.accessi = (int)(Math.random()*(7-1))+1;
    }

    public void run(){
        long permanenza = 2500;
        System.out.println("Prof "+this.id+" chiede "+this.accessi+" accessi");
        lab.entraProf(permanenza, this.id, this.accessi);
    }
}
