// FIRERA SALVO MAT. 578018 A

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchList {
    private LinkedList<String> dirs;
    private Lock lock;
    private Condition readOK;
    private AtomicBoolean end;

    public SynchList() {
        dirs = new LinkedList<>();
        lock = new ReentrantLock();
        readOK = lock.newCondition();
        end = new AtomicBoolean(false);
    }

    public void push(String dir){
        dirs.addFirst(dir);
    }

    public void finish(){
        end.set(true);
    }

    public boolean isFinished(){
        return end.get();
    }

    public String viewLast(){
        return dirs.peekLast();
    }

    public String extractLast(){
        return dirs.pollLast();
    }

    public void lockAquire(){
        lock.lock();
    }

    public void lockRelease(){
        lock.unlock();
    }

    public void waitCond(){
        try {
            readOK.await();
        } catch (InterruptedException e){e.printStackTrace();}
    }

    public void awake(){
        readOK.signalAll();
    }

    public boolean empty(){
        return dirs.isEmpty();
    }
}
