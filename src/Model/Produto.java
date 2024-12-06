package Model;

public class Produto {
    private int id;  // O ID será gerado automaticamente pelo banco de dados
    private String nome;
    private String categoria;
    private int quantidade;
    private double precoCompra;
    private double precoVenda;

    // Construtor sem ID, pois o banco de dados irá gerar o ID automaticamente
    public Produto(String nome, String categoria, int quantidade, double precoCompra, double precoVenda) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
    }

    // Métodos getter e setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }  // O ID pode ser setado após inserção no banco
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoCompra() { return precoCompra; }
    public double getPrecoVenda() { return precoVenda; }
}
