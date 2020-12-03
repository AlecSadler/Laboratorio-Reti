// FIRERA SALVO - 578018 A

public class FullSessionException extends RuntimeException{

    public FullSessionException(){
        super("Su questa sessione non sono disponibili slot liberi!");
    }

}
