import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Produto {
    private String nome;
    private Double preco;

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public String etiquetaPreco() {
        return nome + " R$ " + String.format("%.2f", preco);
    }
}

class ProdutoImportado extends Produto {
    private Double taxaAlfandega;

    public ProdutoImportado(String nome, Double preco, Double taxaAlfandega) {
        super(nome, preco);
        this.taxaAlfandega = taxaAlfandega;
    }

    public Double getTaxaAlfandega() {
        return taxaAlfandega;
    }

    public Double getPreco() {
        return super.getPreco() + taxaAlfandega;
    }

    public String etiquetaPreco() {
        return getNome() + " R$ " + String.format("%.2f", getPreco()) + " (Taxa alfândega: R$ " + String.format("%.2f", taxaAlfandega) + ")";
    }
}

class ProdutoUsado extends Produto {
    private Date dataFabricacao;

    public ProdutoUsado(String nome, Double preco, Date dataFabricacao) {
        super(nome, preco);
        this.dataFabricacao = dataFabricacao;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public String etiquetaPreco() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return getNome() + " (usado) R$ " + String.format("%.2f", getPreco()) + " (Data de fabricação: " + sdf.format(dataFabricacao) + ")";
    }
}

public class Main {                                  
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Produto> produtos = new ArrayList<>();

        System.out.print("Digite o número de produtos: ");
        int n = sc.nextInt();
        sc.nextLine(); 

        for (int i = 1; i <= n; i++) {
            System.out.println("Dados do produto #" + i + ":");
            System.out.print("Comum, usado ou importado (c/u/i)? ");
            char tipo = sc.next().charAt(0);
            sc.nextLine();
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Preço: ");
            double preco = sc.nextDouble();

            if (tipo == 'c') {
                produtos.add(new Produto(nome, preco));
            } else if (tipo == 'u') {
                try {
                    System.out.print("Data de fabricação (dd/MM/yyyy): ");
                    String data = sc.next();
                    Date dataFabricacao = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                    produtos.add(new ProdutoUsado(nome, preco, dataFabricacao));
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
                    sc.nextLine();
                    i--; 
                }
            } else if (tipo == 'i') {
                System.out.print("Taxa de alfândega: ");
                double taxaAlfandega = sc.nextDouble();
                produtos.add(new ProdutoImportado(nome, preco, taxaAlfandega));
            }
        }

        System.out.println();
        System.out.println("ETIQUETAS DE PREÇO:");
        for (Produto produto : produtos) {
            System.out.println(produto.etiquetaPreco());
        }

        sc.close();
    }
}
