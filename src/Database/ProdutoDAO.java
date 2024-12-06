package Database;

import Model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection connection;

    public ProdutoDAO() {
        try {
            // Estabelece a conexão com o banco de dados
            connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/marie/Desktop/AED_POO/database/Banco_Estoque.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO produtos(nome, categoria, quantidade, preco_compra, preco_venda) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Setar os parâmetros
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getCategoria());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, produto.getPrecoCompra());
            statement.setDouble(5, produto.getPrecoVenda());

            // Executa a inserção
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                // Obtém o ID gerado automaticamente
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        produto.setId(generatedKeys.getInt(1));  // Setando o ID gerado
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para retornar todos os produtos
    public List<Produto> getProdutos() {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Cria uma instância de Produto e preenche os dados do banco
                Produto produto = new Produto(
                        resultSet.getString("nome"),
                        resultSet.getString("categoria"),
                        resultSet.getInt("quantidade"),
                        resultSet.getDouble("preco_compra"),
                        resultSet.getDouble("preco_venda")
                );
                produto.setId(resultSet.getInt("id"));  // Preenche o ID
                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // Método para excluir um produto
    public void deleteProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
