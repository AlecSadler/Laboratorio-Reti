import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorINT extends Remote {

    int sum (int a, int b) throws RemoteException;

    int sub (int a, int b) throws RemoteException;

    int mul (int a, int b) throws RemoteException;

    float div (int a, int b) throws IllegalArgumentException, RemoteException;

}
