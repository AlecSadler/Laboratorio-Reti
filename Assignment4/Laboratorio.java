// FIRERA SALVO MAT.578018 A

import java.util.concurrent.atomic.AtomicInteger;

public class Laboratorio {

    //private Object this;

    private int pc[];
    private int attesaProf;
    private int attesaTh;
    private int busy;
    private final long intervallo = 800;

    public Laboratorio(){
        //this.this = new Object();
        this.busy = 0;
        this.pc = new int[20];
        this.attesaProf = 0;
        this.attesaTh = 0;
    }

    public void entraStud(long time, int id, AtomicInteger k) {
        while (k.get() > 0) {
            try {
                int pos;
                synchronized (this) {
                    while (this.attesaProf > 0 || this.attesaTh > 0 || this.busy == 20) {
                        System.out.println("Studente " + id + " in attesa");
                        this.wait();
                    }
                    pos = (int) (Math.random() * 19); // scelgo un pc
                    System.out.println("Entra studente " + id + " in postazione " + pos);
                    this.pc[pos] = 1;
                    this.busy++;
                }
                Thread.sleep(time);
                synchronized (this) {
                    this.busy--;
                    this.pc[pos] = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                this.notifyAll();
            }
            System.out.println("Esce studente " + id);
            try {
                Thread.sleep(this.intervallo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            k.decrementAndGet();
        }
    }

    public void entraTesista(long time, int id, AtomicInteger k){
        while (k.get() > 0){
            int pos = (int)(Math.random()*19);
            synchronized (this) {
                attesaTh++;
                while (attesaProf > 0 || this.pc[pos] == 1 || busy == 20) {
                    try {
                        System.out.println("Tesista " + id + " in attesa");
                        this.wait();
                    } catch (InterruptedException e){e.printStackTrace();}
                    }
                    attesaTh--;
                    System.out.println("Entra tesista " + id + " in postazione " + pos);
                    this.pc[pos] = 1;
                    this.busy++;
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e){e.printStackTrace();}
                synchronized (this) {
                    this.busy--;
                    this.pc[pos]--;
                    this.pc[pos] = 0;
                    System.out.println("Esce tesista "+id);
                    this.notifyAll();
                }
                try {
                    Thread.sleep(this.intervallo);
                } catch (InterruptedException e){e.printStackTrace();}
            k.decrementAndGet();
        }
    }

    public void entraProf(long time, int id, AtomicInteger k){
        while (k.get() > 0){
            synchronized (this) {
                attesaProf++;
                while (this.busy > 0) {
                    try {
                        System.out.println("Prof " + id + " in attesa");
                        this.wait();
                    } catch (InterruptedException e) {e.printStackTrace();}
                }
                    attesaProf--;
                    System.out.println("Entra prof " + id);
                    this.busy = 20;
            }
            try{
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                this.busy = 0;
                this.notifyAll();
                System.out.println("Esce Prof "+id);
            }
            try {
                Thread.sleep(this.intervallo);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            k.decrementAndGet();
        }
    }
}


