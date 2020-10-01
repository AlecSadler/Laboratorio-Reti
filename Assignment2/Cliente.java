// FIRERA SALVO - MAT.578018 A

public class Cliente implements Runnable {
    private int no;
    private boolean servito;

    public Cliente(int n){
        this.no=n;
        this.servito=false;
    }

    public void run(){
        try{
            double servTime = Math.random()*(2000-500+1)+500;
            Thread.sleep((long)servTime,0);
            this.servito=true;
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Sono il cliente " + this.no + " e sono stato servito.");
    }

    public boolean getServito(){
        return this.servito;
    }
}
