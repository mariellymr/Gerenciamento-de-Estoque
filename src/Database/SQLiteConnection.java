package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/marie/Desktop/AED_POO/database/Banco_Estoque.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados.");
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS Produtos (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    categoria TEXT NOT NULL,
                    quantidade INTEGER NOT NULL,
                    preco_compra REAL NOT NULL,
                    preco_venda REAL NOT NULL
                );
            """;
            stmt.execute(sql);
            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar o banco de dados.");
        }
    }
}
