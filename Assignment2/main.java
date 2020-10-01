// FIRERA SALVO - MAT.578018 A

import java.util.Scanner;

public class main {
    public static void main(String[] args){
        Scanner read = new Scanner(System.in);
        int totServiti=0;
        System.out.println("Inserire il numero totale dei clienti: ");
        int nClienti = read.nextInt();
        System.out.println("Inserire parametro k: ");
        int k = read.nextInt();        // capienza sala piccola
        if (k>=nClienti){
            System.out.println("La sala piccola deve contenere meno clienti della principale!");
            System.exit(0);
        }
        int cur = 0;              //cursore nell'array è su 0
        Cliente[] clienti = new Cliente[nClienti];
        Ufficio posta = new Ufficio();
        for (int i=0; i<nClienti; ++i){   // creo una coda mediante un array e un cursore che tiene la posizione dell'ultimo servito
            Cliente cli = new Cliente(i);
            clienti[i] = cli;
        }
        for (int i=0; i<k; ++i) {         // entrano i primi k
            posta.servi(clienti[i]);
        }
        boolean exit=false;
        while (totServiti<nClienti) {
            int j = 0;
            int tmpServ=1;
            while (j < k && !exit && cur+j < nClienti) {
                if (!clienti[cur+j].getServito()){
                    j=0;
                    tmpServ=1;
                    try {
                        Thread.sleep(300,0);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    j++;
                    tmpServ++;
                    if (tmpServ == k || cur+j==nClienti) {
                        exit = true;
                        totServiti += tmpServ;
                        cur += k;
                    }
                }
            }
            exit = false;
            System.out.println("Entrano altri k.");
            for (int i = cur; i < cur + k && i<nClienti; ++i) {   // appena si svuota la sala piccola prendo altri k dai clienti
                posta.servi(clienti[i]);
            }
        }
        System.out.println("La sala grande è vuota.\n");   // chiudo l'ufficio
        posta.close();
        while (!posta.isClosed()){
            continue;
        }
        System.out.println("Ufficio chiuso.\n");
    }
}
