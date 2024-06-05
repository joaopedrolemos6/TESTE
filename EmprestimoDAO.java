import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
    public void fazerEmprestimo(Emprestimo emprestimo) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sql = "INSERT INTO Emprestimos (pessoa_id, livro_id) VALUES (?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, getPessoaId(emprestimo.getPessoa().getCpf(), conexao));
                stmt.setInt(2, getLivroId(emprestimo.getLivro().getTitulo(), conexao));
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fazer empréstimo: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getPessoaId(String cpf, Connection conexao) throws SQLException {
        String sql = "SELECT id FROM Pessoas WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Pessoa não encontrada");
                }
            }
        }
    }

    private int getLivroId(String titulo, Connection conexao) throws SQLException {
        String sql = "SELECT id FROM Livros WHERE titulo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Livro não encontrado");
                }
            }
        }
    }

    public List<Emprestimo> listarEmprestimos() {
        Connection conexao = Conexao.GeraConexao();
        List<Emprestimo> emprestimos = new ArrayList<>();
        if (conexao != null) {
            try {
                String sql = "SELECT e.id, p.nome, l.titulo FROM Emprestimos e " +
                        "JOIN Pessoas p ON e.pessoa_id = p.id " +
                        "JOIN Livros l ON e.livro_id = l.id";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Pessoa pessoa = new Pessoa(rs.getString("nome"), "");
                    Livro livro = new Livro(rs.getString("titulo"), "");
                    Emprestimo emprestimo = new Emprestimo(pessoa, livro);
                    emprestimos.add(emprestimo);
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar empréstimos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return emprestimos;
    }
}
