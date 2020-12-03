// FIRERA SALVO - 578018 A

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main (String[] args){
        try {
            CongressoIMP meeting = new CongressoIMP();
            Congresso stub = (Congresso)UnicastRemoteObject.exportObject(meeting,0);
            LocateRegistry.createRegistry(8888);
            Registry reg = LocateRegistry.getRegistry(8888);
            reg.rebind("CONGRESSO-SERVER", stub);
        } catch (RemoteException e){e.printStackTrace();}
        System.out.println("Server pronto.");


    }
}
