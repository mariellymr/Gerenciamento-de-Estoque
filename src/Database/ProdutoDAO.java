package Database;

import Model.Produto;
import java.sql.*;

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
}
