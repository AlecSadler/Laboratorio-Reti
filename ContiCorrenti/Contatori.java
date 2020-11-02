public class Contatori {
    private int noBonifici;
    private int noAccrediti;
    private int noBollettini;
    private int noBancomat;
    private int noF24;

    public Contatori(){
        noAccrediti = 0;
        noBancomat = 0;
        noBollettini = 0;
        noBonifici = 0;
        noF24 = 0;
    }

    public synchronized void incAccrediti(){
        noAccrediti++;
    }

    public synchronized void incBancomat(){
        noBancomat++;
    }

    public synchronized void incBonifici(){
        noBonifici++;
    }

    public synchronized void incBollettini(){
        noBollettini++;
    }

    public synchronized void incF24(){
        noF24++;
    }

    public void printStatus(){
        System.out.println("Riepilogo Operazioni:");
        System.out.println("Accrediti: "+noAccrediti);
        System.out.println("Bonifici: "+noBonifici);
        System.out.println("Pagamenti F24: "+noF24);
        System.out.println("Bolettini: "+noBollettini);
        System.out.println("PagoBancomat: "+noBancomat);
        int tot = noAccrediti + noBancomat + noBollettini + noF24 + noBonifici;
        System.out.println("Totale generale: "+tot);

    }
}
