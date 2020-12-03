// FIRERA SALVO - 578018 A

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;

public class CongressoIMP extends RemoteServer implements Congresso{

    private ArrayList<ArrayList<ArrayList<String>>> plan;
    private final int days = 3;
    private final int dailySessions = 12;
    private final int sessSlots = 5;

    // costruttore
    public CongressoIMP(){
        plan = new ArrayList<>(days);
        for (int i=0; i<days; ++i){
            plan.add(i, new ArrayList<>(dailySessions));
        }
        for (int i=0; i<days; ++i){
            for (int j=0; j<dailySessions; ++j){
                plan.get(i).add(j, new ArrayList<>(sessSlots));
            }
        }
    }

    @Override
    public synchronized boolean subscibe(String name, int giorno, int sessione) throws FullSessionException, InvalidParametersException, RemoteException {
        if (giorno<0 || giorno>2 || sessione<0 || sessione>11 ) {
            throw new InvalidParametersException();
        }
        if (plan.get(giorno).get(sessione).size() < sessSlots){
            return plan.get(giorno).get(sessione).add(name);
        }
        throw new FullSessionException();
    }

    @Override
    public ArrayList<ArrayList<String>> getDailySchedule(int giorno) throws InvalidParametersException, RemoteException  {
        if (giorno<0 || giorno>2) throw new InvalidParametersException();
        return plan.get(giorno);
    }
}
