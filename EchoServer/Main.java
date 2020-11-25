public class Main {
    public static void main(String[] args) {
        Server testS = new Server(6789);
        Client testC = new Client(6789);

        Thread s = new Thread(testS);
        Thread c = new Thread(testC);

        s.start();
        c.start();

        /* questo blocco fa si che se il client ha temrinato con il comando "exit", il server viene
           interrotto e terminerà anch'esso (vedendo l'interruzione chiude la connessione)
         */
        while (c.isAlive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        s.interrupt();

        try {
            s.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

