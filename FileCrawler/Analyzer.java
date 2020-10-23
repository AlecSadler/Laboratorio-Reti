// FIRERA SALVO MAT. 578018 A

import java.io.File;

public class Analyzer implements Runnable {
    private String path;
    private SynchList dirList;

    public Analyzer(SynchList l, String pt){
        dirList = l;
        path = pt;
    }

    private void analyze(String path) {
        File file = new File(path);
        File[] list = file.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    dirList.lockAquire();
                    dirList.push(f.getAbsolutePath());
                    dirList.awake();
                    dirList.lockRelease();
                    System.out.println("Trovata directory:" + f.getName());
                    analyze(f.getAbsolutePath());
                }
            }
        }
    }

    public void run(){ 
        analyze(path);
        dirList.lockAquire();
        dirList.push("Stop");
        dirList.finish();
        dirList.awake();
        dirList.lockRelease();
    }

}

