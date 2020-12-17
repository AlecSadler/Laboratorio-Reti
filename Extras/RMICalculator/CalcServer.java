import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalcServer {

    private static int port = 8888;

    public static void main (String[] args) {
        try {
            CalculatorIMPL RemoteCalc = new CalculatorIMPL();
            CalculatorINT stub = (CalculatorINT) UnicastRemoteObject.exportObject(RemoteCalc, 0);
            LocateRegistry.createRegistry(port);
            Registry reg = LocateRegistry.getRegistry(port);
            reg.rebind("RemoteCalculator", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(">>> WELCOME TO REMOTE CALCULATOR <<<");
    }
}
