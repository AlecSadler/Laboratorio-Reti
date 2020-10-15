import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Laboratorio {
    private Lock lockLab;

    private Condition condS;
    private Condition condT;
    private Condition condP;

    private int pc[];
    private int busy;
    private final long intervallo = 800;

    public Laboratorio(){
        this.busy = 0;
        this.pc = new int[20];
        this.lockLab = new ReentrantLock();
        this.condP = lockLab.newCondition();
        this.condT = lockLab.newCondition();
        this.condS = lockLab.newCondition();
    }

    public void entraStud(long time, int id, int k){
        while (k > 0){
            this.lockLab.lock();
            try {
                while (this.busy == 20) {
                    this.condS.await();
                }
                int pos = (int) (Math.random() * 19); // scelgo un pc
                System.out.println("Entra studente "+id+" in postazione "+pos);
                this.pc[pos] = 1;
                this.busy++;
                this.lockLab.unlock();
                Thread.sleep(time);
                this.lockLab.lock();
                this.busy--;
                this.pc[pos] = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.condP.signal();
            this.condT.signalAll();
            this.condS.signalAll();
            System.out.println("Esce studente "+id);
            this.lockLab.unlock();
            try {
                Thread.sleep(this.intervallo);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            k--;
        }
    }

    public void entraTesista(long time, int id, int k){
        while (k > 0){
            this.lockLab.lock();
            int pos = (int)(Math.random()*19);
            try {
                while (this.pc[pos] == 1 || this.busy == 20) {
                    this.condT.await();
                }
                System.out.println("Entra tesista "+id+" in postazione "+pos);
                this.pc[pos] = 1;
                this.busy++;
                this.lockLab.unlock();
                Thread.sleep(time);
                this.lockLab.lock();
                this.busy--;
                this.pc[pos]--;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            this.condP.signal();
            this.condT.signalAll();
            this.condS.signalAll();
            System.out.println("Esce tesista "+id);
            this.lockLab.unlock();
            try {
                Thread.sleep(this.intervallo);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            k--;
        }
    }

    public void entraProf(long time, int id, int k){
        while (k > 0){
            this.lockLab.lock();
            try {
                while (this.busy > 0){
                    this.condP.await();
                }
                System.out.println("Entra prof "+id);
                this.busy = 20;
                this.lockLab.unlock();
                Thread.sleep(time);
                this.lockLab.lock();
                this.busy = 0;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            this.condP.signal();
            this.condT.signalAll();
            this.condS.signalAll();
            System.out.println("Esce prof "+id);
            this.lockLab.unlock();
            try {
                Thread.sleep(this.intervallo);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            k--;
        }
    }
}


