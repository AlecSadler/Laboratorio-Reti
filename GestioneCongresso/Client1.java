// FIRERA SALVO - 578018 A

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client1 {
    public static void main (String[] args){
        String nome = null;
        Scanner in = new Scanner(System.in);
        System.out.print("Inserisci il tuo nome: ");
        nome = in.nextLine();
        Registry reg;
        Remote remObj;
        Congresso meeting;
        try {
            reg = LocateRegistry.getRegistry(8888);
            remObj = reg.lookup("CONGRESSO-SERVER");
            meeting = (Congresso)remObj;

            boolean exit = false;
            while (!exit){
                System.out.println("### SELEZIONA L'OPERAZIONE DESIDERARATA ###");
                System.out.println("PRENOTAZIONE SLOT PER CONFERENZA (1) - CONSULTAZIONE PROGRAMMA GIORNATA (2) - ESCI (99)");
                int func = in.nextInt();
                int giorno, sessione;
                if (func==99){
                    System.out.println("Uscita dal programma...");
                    exit = true;
                    break;
                }
                else if (func==1){
                    System.out.println("Digita il giorno in cui vuoi presentare (0-1-2).");
                    giorno = in.nextInt();
                    System.out.println("Digita il numero di sessione a cui iscriverti (0->11)");
                    sessione = in.nextInt();
                    try {
                        if (meeting.subscibe(nome, giorno, sessione)) {
                            System.out.println("Iscrizione avvenuta correttamente!");
                        }
                        else System.out.println("Errore durante l'iscrizione");
                    }catch (InvalidParametersException | FullSessionException e) {System.out.println(e.getMessage());}
                    continue;
                }
                else if (func==2){
                    System.out.println("Digita il giorno a cui sei interessato (0-1-2)");
                    giorno = in.nextInt();
                    try {
                        ArrayList<ArrayList<String>> daySchedule = meeting.getDailySchedule(giorno);
                        System.out.println("Programma interventi del giorno "+giorno);
                        for (int i=0; i<daySchedule.size(); ++i){
                            System.out.println("Sessione: "+i);
                            for (int j=0; j<daySchedule.get(i).size(); ++j){
                                System.out.println("Slot: "+j+" Presenta: "+daySchedule.get(i).get(j));
                            }
                        }
                    } catch (InvalidParametersException e){System.out.println(e.getMessage());}
                }
                else{
                    System.out.println("Specificare un'opzione valida!");
                    continue;
                }
            }
        } catch (RemoteException | NotBoundException e) {e.printStackTrace();}
    }
}
