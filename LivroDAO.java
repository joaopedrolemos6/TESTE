import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    public void adicionarLivro(Livro livro) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sql = "INSERT INTO Livros (titulo, autor) VALUES (?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao adicionar livro: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Livro encontrarLivroPorTitulo(String titulo) {
        Connection conexao = Conexao.GeraConexao();
        Livro livro = null;
        if (conexao != null) {
            try {
                String sql = "SELECT * FROM Livros WHERE titulo = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, titulo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    livro = new Livro(rs.getString("titulo"), rs.getString("autor"));
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao encontrar livro por título: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return livro;
    }

    public List<Livro> listarLivros() {
        Connection conexao = Conexao.GeraConexao();
        List<Livro> livros = new ArrayList<>();
        if (conexao != null) {
            try {
                String sql = "SELECT * FROM Livros";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Livro livro = new Livro(rs.getString("titulo"), rs.getString("autor"));
                    livros.add(livro);
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar livros: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return livros;
    }
}