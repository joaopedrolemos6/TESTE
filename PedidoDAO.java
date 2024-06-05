import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private int getPessoaId(String cpf) throws SQLException {
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
                } else {
                    throw new SQLException("ID da pessoa não encontrado para o CPF: " + cpf);
                }
                rs.close(); // Fechar o ResultSet
                stmt.close();
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