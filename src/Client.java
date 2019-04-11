import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;


public class Client extends JFrame {

    //Variaveis para guardar parametro
    public String moedaSelecionada = "";
    public double cotacaoSelecionada = 0.0;

    //Variavel que vai ser atualizada pelo servidor
    private ArrayList<Moeda> listamoeda = new ArrayList<Moeda>();

    //Variavel para armazenamento dos checkbox
    private ArrayList<JCheckBox> listaCheck = new ArrayList<JCheckBox>();

    Container panel = getContentPane();

    public Client(){

        //Carrega a lista de moedas do servidor
        Calculadora c = null;
        try {
            c = (Calculadora) Naming.lookup("rmi://localhost/joao");
            listamoeda = c.getMoedas();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //Cria os elementos do layout
        final JLabel t1 = new JLabel("Moeda:");
        final JTextField n1 = new JTextField(5);
        final JLabel t2 = new JLabel("Cotacao:");
        final JTextField n2 = new JTextField(5);
        final JLabel t3 = new JLabel("Valor:");
        final JTextField n3 = new JTextField(5);
        final JButton Calcular = new JButton("Converter");
        final JButton Modificar = new JButton("Modificar");
        final JButton Adicionar = new JButton("Adicionar");
        final JButton Remover = new JButton("Remover");
        final JTextArea area = new JTextArea(30, 30);

        //seta o tipo de layout
        panel.setLayout(new FlowLayout());

        //Adiciona os JLabel e JTextField ao layout
        panel.add(t1);
        panel.add(n1);
        panel.add(t2);
        panel.add(n2);
        panel.add(t3);
        panel.add(n3);

        //Adiciona os botoes ao layout
        panel.add(Calcular);
        panel.add(Adicionar);
        panel.add(Modificar);
        panel.add(Remover);

        //Criando os checkbox baseado na lista de moedas e adicionando eles no panel e cria o item listener
        for (int i = 0; i < this.listamoeda.size() ; i++) {
            final JCheckBox aux = new JCheckBox(this.listamoeda.get(i).nome);
            this.listaCheck.add(aux);
            panel.add(this.listaCheck.get(i));

            int finalJ = i;
            this.listaCheck.get(i).addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange()==1){

                        n1.setText(listamoeda.get(finalJ).nome);

                        for (int i = 0; i < listaCheck.size() ; i++) {
                            listaCheck.get(i).setSelected(false);
                        }

                        //define a moedaSelecionada
                        moedaSelecionada = listamoeda.get(finalJ).nome;

                        try {
                            //pega a cotacao do servidor
                            Calculadora c = (Calculadora) Naming.lookup("rmi://localhost/joao");
                            cotacaoSelecionada = c.getCotacao(moedaSelecionada);

                        } catch (NotBoundException e1) {
                            e1.printStackTrace();

                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                        //coloca a cotacao na caixa de texto
                        n2.setText( String.valueOf(cotacaoSelecionada));
                    }
                }
            });
        }

        //Adiciona a caixa de texto ao layout
        panel.add(area);


        Calcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    double cotacao = Double.parseDouble(n2.getText());
                    double valor = Double.parseDouble(n3.getText());
                    Calculadora c = (Calculadora) Naming.lookup("rmi://localhost/joao");
                    double resultado = c.converter(cotacao, valor);
                    area.append( valor + " reais dao " + resultado+" na moeda selecionada\n");

                } catch (NumberFormatException e1) {
                    area.append("Erro de tipagem numerica ou entrada nula\n");
                } catch (IOException e1) {
                    area.append("Erro de entrada/saida\n");
                }catch (NotBoundException e1){
                    System.out.println(e1);
                }
            }
        });

        Modificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double cotacao = Double.parseDouble(n2.getText());
                    Calculadora c = (Calculadora) Naming.lookup("rmi://localhost/joao");
                    if(c.modificar(moedaSelecionada,cotacao)==1){
                    area.append("Mudou (Moeda: " + moedaSelecionada + " ,Cotacao: " + cotacao+ "): " +"\n");
                    }else{
                        area.append("Essa moeda nao exite no sistema\n");
                    }

                } catch (NumberFormatException e1) {
                    area.append("Erro de tipagem numerica ou entrada nula\n");
                } catch (IOException e1) {
                    area.append("Erro de entrada/saida\n");
                }catch (NotBoundException e1){
                    System.out.println(e1);
                }
            }
        });

        Adicionar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = n1.getText();
                    double cotacao = Double.parseDouble(n2.getText());
                    Calculadora c = (Calculadora) Naming.lookup("rmi://localhost/joao");
                    if(c.addMoeda(nome,cotacao) == 1) {
                        area.append("Adicionou a moeda " + nome + " com a cotacao " + cotacao + "\n");
                        area.append("(OBS) Execute novamente o Cliente.java\n");
                    }else{
                        area.append("Essa moeda ja existe no sistema\n");
                    }

                } catch (NumberFormatException e1) {
                    area.append("Erro de tipagem numerica ou entrada nula\n");
                } catch (IOException e1) {
                    area.append("Erro de entrada/saida\n");
                }catch (NotBoundException e1){
                    System.out.println(e1);
                }
            }
        });

        Remover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Calculadora c = (Calculadora) Naming.lookup("rmi://localhost/joao");
                    if(c.excMoeda(moedaSelecionada) == 1){
                        area.append("Apagou a moeda " + moedaSelecionada + "\n");
                        area.append("(OBS) Execute novamente o Cliente.java\n");
                    }else{
                        area.append("Essa moeda nao exite no sistema\n");
                    }

                } catch (NumberFormatException e1) {
                    area.append("Erro de tipagem numerica ou entrada nula\n");
                } catch (IOException e1) {
                    area.append("Erro de entrada/saida\n");
                }catch (NotBoundException e1){
                    System.out.println(e1);
                }
            }
        });

    }



    public static void main(String[] args)
    {
            Client frame = new Client();

            frame.setTitle("Cliente SD");
            frame.setSize(450,300);
            frame.setVisible(true);


            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });


    }
}