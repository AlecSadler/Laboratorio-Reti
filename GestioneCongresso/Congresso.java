// FIRERA SALVO - 578018 A

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Congresso extends Remote {

    // permette allo speaker di iscriversi a uno slot della sessione
    public  boolean subscibe(String name, int giorno, int sessione) throws FullSessionException, InvalidParametersException, RemoteException;

    // permette di consultare il programma di interventi della giornata
    public ArrayList<ArrayList<String>> getDailySchedule(int giorno) throws InvalidParametersException, RemoteException;

}
