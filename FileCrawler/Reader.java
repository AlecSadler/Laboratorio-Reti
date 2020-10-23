// FIRERA SALVO MAT. 578018 A

import java.io.File;

public class Reader implements Runnable {
    private SynchList dirList;

    public Reader(SynchList l){
        dirList = l;
    }

    public void run() {
        while (!dirList.isFinished()) {
            dirList.lockAquire();
            while(dirList.empty()) {
                dirList.waitCond();
            }
            if (dirList.viewLast().equals("Stop")) {   // se incontro stop termino
                dirList.lockRelease();
                return;
            }
            File file = new File(dirList.extractLast());
            dirList.lockRelease();
            File[] list = file.listFiles();
            for (File f : list) {
                if (f.isFile()) {
                    System.out.println("File: " + f.getName() + " Lavora il reader: " + Thread.currentThread().getName());
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " termina");
    }
}
