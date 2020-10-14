// FIRERA SALVO MAT.578018 A

public class Tesista implements Runnable {
    private Laboratorio lab = null;
    private int id;
    private int accessi;

    public Tesista(Laboratorio l, int id){
        this.lab = l;
        this.accessi = (int)(Math.random()*(7-1))+1;
        this.id = id;
    }

    public void run(){
        long permanenza = 1800;
        System.out.println("Tesista "+this.id+" chiede "+this.accessi+" accessi");
        this.lab.entraTesista(permanenza, this.id, this.accessi);
    }
}
