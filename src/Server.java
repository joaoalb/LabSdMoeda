import java.rmi.*;


public class Server {

    public static void main(String[] args) throws RemoteException, java.net.MalformedURLException {
        CalculadoraServer es = new CalculadoraServer();
        Naming.rebind("rmi://localhost/joao",es);
    }

}
