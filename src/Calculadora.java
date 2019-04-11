import java.rmi.*;
import java.util.ArrayList;


public interface Calculadora extends Remote {
    public ArrayList<Moeda> getMoedas() throws RemoteException;
    public double converter(double cotacao, double valor) throws RemoteException;
    public int modificar(String nome ,double cotacao) throws RemoteException;
    public double getCotacao(String nome) throws RemoteException;
    public int addMoeda(String nome, double cotacao) throws RemoteException;
    public  int excMoeda(String nome) throws RemoteException;
}