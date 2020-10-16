// FIRERA SALVO MAT.578018 A

import java.util.concurrent.atomic.AtomicInteger;

public class Studente implements Runnable {
    private int id;
    private AtomicInteger accessi;
    private Laboratorio lab = null;

    public Studente(int id, Laboratorio l) {
        this.id = id;
        this.lab = l;
        int k = (int) (Math.random() * (7 - 1)) + 1; //suppongo un massimo di 7 accessi per evitare tempi eccessivi
        this.accessi = new AtomicInteger();
        this.accessi.set(k);
    }

    public void run() {
        long permanenza = 1000;
        System.out.println("Studente " + this.id + " chiede " + this.accessi + " accessi");
        lab.entraStud(permanenza, this.id, this.accessi);
    }
}




