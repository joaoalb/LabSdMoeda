import java.lang.reflect.Constructor;
import java.io.Serializable;


public class Moeda implements Serializable{

    public String nome;
    public double cotacao;

    public Moeda(String nome, double cotacao){
        this.nome = nome;
        this.cotacao = cotacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCotacao(double cotacao) {
        this.cotacao = cotacao;
    }

    public String getNome() {
        return nome;
    }

    public double getCotacao() {
        return cotacao;
    }
}
