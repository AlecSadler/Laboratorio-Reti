import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

public class CalculatorIMPL extends RemoteServer implements CalculatorINT {

    // Contructor
    public CalculatorIMPL() {
        super();
    }

    @Override
    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public int sub(int a, int b) throws RemoteException {
        return a - b;
    }

    @Override
    public int mul(int a, int b) throws RemoteException {
        return a * b;
    }

    @Override
    public float div(int a, int b) throws IllegalArgumentException, RemoteException {
        if (b == 0) {
            throw new IllegalArgumentException();
        }
        return (float) a / (float) b;
    }
}


