public class piGreco implements Runnable {
    private double precision;

    public piGreco(double prec){
        this.precision=prec;
    }

    public void run(){
        double p=0;
        long start= System.currentTimeMillis(), finish, duration;
        int den=1;
        boolean exit=false;
        while (!exit){
            p= p+ (double)4/den;
            System.out.println("Approx pi = " + p);
            if ( Math.abs(p - Math.PI) <= this.precision || Thread.currentThread().isInterrupted()){
                finish=System.currentTimeMillis();
                duration= finish - start;
                if (Thread.currentThread().isInterrupted()) System.out.println("Mi ha interrotto il main dopo " + duration + " ms");
                else System.out.println("Ho finito da solo impiegando " + duration + " ms");
                exit=true;
            }
            den=-den;
            if (den>0) den=den+2;
            else den=den-2;
        }
    }
}
