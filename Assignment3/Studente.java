// FIRERA SALVO MAT.578018 A

public class Studente implements Runnable {
    private int id;
    private int accessi;
    private Laboratorio lab = null;
    private int postazione;

    public Studente(int id, Laboratorio l){
        this.id = id;
        this.lab = l;
        this.accessi = (int)(Math.random()*(7-1))+1; //suppongo un massimo di 7 accessi per evitare tempi eccessivi
    }

    public void run(){
        long permanenza = 1000;
        System.out.println("Studente "+this.id+" chiede "+this.accessi+" accessi");
        lab.entraStud(permanenza,this.id,this.accessi);
    }

}


