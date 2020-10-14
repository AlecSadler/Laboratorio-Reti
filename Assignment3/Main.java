// FIRERA SALVO MAT.578018 A

public class Main {
    public static void main (String[] args) {
        // inizializzazione laboratorio
        Laboratorio lab = new Laboratorio();

        // inizializzazione studenti
        Studente mario = new Studente(1,lab);
        Studente giuseppe = new Studente(2,lab);
        Studente carmelo = new Studente(3,lab);
        Thread s1 = new Thread(mario);
        Thread s2 = new Thread(giuseppe);
        Thread s3 = new Thread(carmelo);

        // inizializzazione tesisti
        Tesista lucia = new Tesista(lab, 1);
        Tesista maria = new Tesista(lab, 2);
        Tesista alfredo = new Tesista(lab, 3);
        Thread t1 = new Thread(lucia);
        Thread t2 = new Thread(maria);
        Thread t3 = new Thread(alfredo);

        // inizializzazione professori
        Prof rossi = new Prof(lab,1);
        Prof verdi = new Prof(lab,2);
        Thread p1 = new Thread(rossi);
        Thread p2 = new Thread(verdi);

        // Avvio threads
        s1.start();
        s2.start();
        s3.start();

        t1.start();
        t2.start();
        t3.start();

        p1.start();
        p2.start();
    }
}
