// FIRERA SALVO MAT. 578018 A

public class Main {
    public static void main (String[] args){
        SynchList directories = new SynchList();

        Analyzer writer = new Analyzer(directories, "./Example");
        Thread w = new Thread(writer);

        Reader[] cons = new Reader[4];
        Thread[] c = new Thread[4];

        for (int k=0; k<4; k++){  // suppongo k=4 consumatori
            cons[k] = new Reader(directories);
            c[k] = new Thread(cons[k]);
            c[k].start();
        }

        w.start();

        for (int k=0; k<4; k++){
            try {
                c[k].join();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        try {
            w.join();
        }  catch (InterruptedException e){e.printStackTrace();}
    }
}
