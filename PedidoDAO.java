import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void fazerPedido(Pedido pedido) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sql = "INSERT INTO Pedidos (pessoa_id, valor) VALUES (?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, getPessoaId(pedido.getPessoa().getCpf()));
                stmt.setDouble(2, pedido.getValor());
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fazer pedido: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Pedido> listarPedidos() {
        Connection conexao = Conexao.GeraConexao();
        List<Pedido> pedidos = new ArrayList<>();
        if (conexao != null) {
            try {
                String sql = "SELECT p.id, p.nome, p.cpf, pd.valor " +
                        "FROM Pedidos pd " +
                        "JOIN Pessoas p ON pd.pessoa_id = p.id";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    double valor = rs.getDouble("valor");
                    Pessoa pessoa = new Pessoa(nome, cpf); // Ajuste aqui conforme a classe Pessoa
                    Pedido pedido = new Pedido(pessoa, valor);
                    pedidos.add(pedido);
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar pedidos: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pedidos;
    }

    private int getPessoaId(String cpf) {
        Connection conexao = Conexao.GeraConexao();
        int pessoaId = -1;
        if (conexao != null) {
            try {
                String sql = "SELECT id FROM Pessoas WHERE cpf = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pessoaId = rs.getInt("id");
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao buscar ID da pessoa: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pessoaId;
    }
}
