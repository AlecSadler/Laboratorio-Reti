// ThreadPool che esamina i conti correnti
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Workers {
    private ThreadPoolExecutor executors;

    public Workers(int nTh){
        executors = (ThreadPoolExecutor) Executors.newFixedThreadPool(nTh);
    }

    public void exec(AnalizzaMov task) {
        executors.execute(task);
    }

    public void termina(){
        executors.shutdown();
    }

    public boolean finish(){
        return executors.isTerminated();
    }

}
