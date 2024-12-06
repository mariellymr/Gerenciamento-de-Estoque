package View;

import Database.ProdutoDAO;
import Model.Produto;
import Database.SQLiteConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainApp extends JFrame {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public MainApp() {
        setTitle("Sistema de Gerenciamento de Estoque");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar tabela
        String[] columnNames = {"ID", "Nome", "Categoria", "Quantidade", "Preço Compra", "Preço Venda"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Adicionar painel de botões
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> showAddForm());
        deleteButton.addActionListener(e -> deleteProduto());
        loadProdutos();
    }

    private void loadProdutos() {
        tableModel.setRowCount(0);
        List<Produto> produtos = produtoDAO.getProdutos();
        for (Produto p : produtos) {
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getCategoria(), p.getQuantidade(), p.getPrecoCompra(), p.getPrecoVenda()});
        }
    }

    private void showAddForm() {
        JDialog addDialog = new JDialog(this, "Adicionar Produto", true);  // Janela modal
        addDialog.setLayout(new GridLayout(6, 2));
    
        JLabel lblNome = new JLabel("Nome:");
        JTextField tfNome = new JTextField();
        JLabel lblCategoria = new JLabel("Categoria:");
        JTextField tfCategoria = new JTextField();
        JLabel lblQuantidade = new JLabel("Quantidade:");
        JTextField tfQuantidade = new JTextField();
        JLabel lblPrecoCompra = new JLabel("Preço Compra:");
        JTextField tfPrecoCompra = new JTextField();
        JLabel lblPrecoVenda = new JLabel("Preço Venda:");
        JTextField tfPrecoVenda = new JTextField();
    
        JButton btnSalvar = new JButton("Salvar");
    
        addDialog.add(lblNome);
        addDialog.add(tfNome);
        addDialog.add(lblCategoria);
        addDialog.add(tfCategoria);
        addDialog.add(lblQuantidade);
        addDialog.add(tfQuantidade);
        addDialog.add(lblPrecoCompra);
        addDialog.add(tfPrecoCompra);
        addDialog.add(lblPrecoVenda);
        addDialog.add(tfPrecoVenda);
        addDialog.add(new JLabel());  // Deixa um espaço vazio
        addDialog.add(btnSalvar);
    
        btnSalvar.addActionListener(e -> {
            // Validação e salvamento dos dados
            String nome = tfNome.getText();
            String categoria = tfCategoria.getText();
            int quantidade = Integer.parseInt(tfQuantidade.getText());
            double precoCompra = Double.parseDouble(tfPrecoCompra.getText());
            double precoVenda = Double.parseDouble(tfPrecoVenda.getText());
    
            Produto novoProduto = new Produto(nome, categoria, quantidade, precoCompra, precoVenda);
            produtoDAO.adicionarProduto(novoProduto);
    
            loadProdutos();  // Atualiza a tabela
            addDialog.dispose();  // Fecha o formulário
        });
    
        addDialog.setSize(300, 300);
        addDialog.setLocationRelativeTo(this);  // Centraliza a janela
        addDialog.setVisible(true);
    }
    

    private void deleteProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            produtoDAO.deleteProduto(id);
            loadProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SQLiteConnection.initializeDatabase();
            new MainApp().setVisible(true);
        });
    }
}
