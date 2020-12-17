import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private static int port = 8888;

    public static void main (String[] args){
        Scanner in = new Scanner(System.in);
        Registry reg;
        Remote remoteObj;
        CalculatorINT calc;
        try {
            reg = LocateRegistry.getRegistry(port);
            remoteObj = reg.lookup("RemoteCalculator");
            calc = (CalculatorINT) remoteObj;
            boolean exit = false;
            while (!exit) {
                float res;
                System.out.println("Insert a as integer: ");
                int a = in.nextInt();
                System.out.println("Insert b as integer: ");
                int b = in.nextInt();

                res = calc.sum(a, b);
                System.out.println("Sum(a,b) = " + res);

                res = calc.sub(a, b);
                System.out.println("Subtract(a,b) = " + res);

                res = calc.mul(a, b);
                System.out.println("Multiplication(a,b) = " + res);

                try {
                    res = calc.div(a, b);
                    System.out.println("Division(a,b) = " + res);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                int end;
                System.out.println("Do you want to insert new values? (1)YES / (2)NO ");
                end = in.nextInt();
                if (end == 2) {exit = true;}
            }
        } catch (RemoteException | NotBoundException e){e.printStackTrace();}
    }
}
