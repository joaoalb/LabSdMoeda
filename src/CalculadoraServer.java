import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;


public class CalculadoraServer extends UnicastRemoteObject implements Calculadora {

    //Variavel para guardar as moedas do servidor
    private ArrayList<Moeda> moedasDoSistema = new ArrayList<Moeda>();

    //Variaveis para guardar as moedas base do servidor
    public String[] nomesB = {"Dolar","Euro","Iene","Libra"};
    private double[] cotacoesB = {4.0, 4.0, 0.03, 5.0};

    //GeeksForGeeks comparacao de string
    //https://www.geeksforgeeks.org/compare-two-strings-in-java/
    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        // Edge case for strings like
        // String 1="Geeks" and String 2="Geeksforgeeks"
        if (l1 != l2) {
            return l1 - l2;
        }

        // If none of the above conditions is true,
        // it implies both the strings are equal
        else {
            return 0;
        }
    }

    //Funcao para inmiciar o servidor
    public CalculadoraServer() throws RemoteException{
        System.out.println("Iniciou o servidor");
        //Carrega as cotacoes base
        for (int i = 0; i < 4; i++) {
            moedasDoSistema.add(new Moeda(nomesB[i],cotacoesB[i]));
        }

    }

    //Funcao para pegar as moedas que estao cadastradas no servidor
    public ArrayList<Moeda> getMoedas() {
        return moedasDoSistema;
    }

    //Funcao para converter uma moeda em relacao a uma cotacao
    public double converter(double cotacao, double valor) throws RemoteException{
        double resultado;
        resultado = (valor/cotacao);
        System.out.println("Converteu: "+resultado);
        return resultado;
    }

    //Funcao para modificar a cotacao de uma moeda no servidor
    public int modificar(String nome, double cotacao) throws RemoteException{
        int len=moedasDoSistema.size();
        for(int i=0; i<len; i++) {
            System.out.println(moedasDoSistema.get(i).getNome()+" "+nome);
            if (stringCompare(moedasDoSistema.get(i).getNome(),nome) == 0) {
                moedasDoSistema.get(i).cotacao = cotacao;
                System.out.println("Mudou a cotacao: "+moedasDoSistema.get(i).cotacao);
                return 1;
            }
        }

        return 0;
    }

    //Funcao para pegar a cotacao da moeda no servidor
    public double getCotacao(String nome) throws RemoteException{
        int len=moedasDoSistema.size();
        for(int i=0; i<len; i++) {
            System.out.println(moedasDoSistema.get(i).getNome()+" "+nome);
            if (stringCompare(moedasDoSistema.get(i).getNome(),nome) == 0) {
                System.out.println("Enviou cotacao: "+moedasDoSistema.get(i).cotacao);
                return moedasDoSistema.get(i).cotacao;
            }
        }
        return 0;
    }

    //Funcao para adicionar uma moeda no servidor
    public int addMoeda(String nome, double cotacao){
        int len=moedasDoSistema.size();
        for(int i=0; i<len; i++) {
            System.out.println(moedasDoSistema.get(i).getNome()+" "+nome);
            if (stringCompare(moedasDoSistema.get(i).getNome(),nome) == 0) {
                System.out.println("Ja tem essa moeda");
                return 0;
            }
        }
        System.out.println("nome: "+nome +", cotacao: "+cotacao);
        moedasDoSistema.add(new Moeda(nome,cotacao));
        return  1;
    }



    //Funcao para excluir a moeda do servidor
    public  int excMoeda(String nome){
        System.out.println("Procurando moeda:"+nome);
        int len=moedasDoSistema.size();
        for(int i=0; i<len; i++) {
            System.out.println(moedasDoSistema.get(i).getNome()+" "+nome);

            if (stringCompare(moedasDoSistema.get(i).getNome(),nome) == 0) {
                moedasDoSistema.remove(i);
                System.out.println("Apagou");
                return 1;
            }
        }

        return 0;

    }

    public static void main(String[] args) {
    }
}  