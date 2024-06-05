import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // Método para cadastrar um novo aluno no banco de dados
    public void cadastrarAluno(Aluno aluno) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sqlPessoa = "INSERT INTO Pessoas (nome, cpf, tipo) VALUES (?, ?, 'Aluno')";
                String sqlAluno = "INSERT INTO Alunos (id, notaProva1, notaProva2) VALUES (LAST_INSERT_ID(), 0, 0)";
                PreparedStatement stmtPessoa = conexao.prepareStatement(sqlPessoa);
                PreparedStatement stmtAluno = conexao.prepareStatement(sqlAluno);

                // Inserir na tabela Pessoas
                stmtPessoa.setString(1, aluno.getNome());
                stmtPessoa.setString(2, aluno.getCpf());
                stmtPessoa.executeUpdate();

                // Inserir na tabela Alunos
                stmtAluno.executeUpdate();

                stmtPessoa.close();
                stmtAluno.close();
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
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

    // Método para encontrar um aluno pelo CPF
    public Aluno encontrarAlunoPorCpf(String cpf) {
        Connection conexao = Conexao.GeraConexao();
        Aluno aluno = null;
        if (conexao != null) {
            try {
                String sql = "SELECT p.id, p.nome, p.cpf, a.notaProva1, a.notaProva2 FROM Pessoas p INNER JOIN Alunos a ON p.id = a.id WHERE p.cpf = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    aluno = new Aluno(rs.getString("nome"), rs.getString("cpf"));
                    aluno.setNotaProva1(rs.getDouble("notaProva1"));
                    aluno.setNotaProva2(rs.getDouble("notaProva2"));
                }

                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao encontrar aluno: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return aluno;
    }

    // Método para atualizar as notas das provas de um aluno
    public void atualizarNotasAluno(String cpf, double notaProva1, double notaProva2) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sql = "UPDATE Alunos a INNER JOIN Pessoas p ON a.id = p.id SET a.notaProva1 = ?, a.notaProva2 = ? WHERE p.cpf = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);

                stmt.setDouble(1, notaProva1);
                stmt.setDouble(2, notaProva2);
                stmt.setString(3, cpf);
                stmt.executeUpdate();

                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar notas do aluno: " + e.getMessage());
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

    // Método para listar todos os alunos
    public List<Aluno> listarAlunos() {
        Connection conexao = Conexao.GeraConexao();
        List<Aluno> alunos = new ArrayList<>();
        if (conexao != null) {
            try {
                String sql = "SELECT p.id, p.nome, p.cpf, a.notaProva1, a.notaProva2 FROM Pessoas p INNER JOIN Alunos a ON p.id = a.id";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("cpf"));
                    aluno.setNotaProva1(rs.getDouble("notaProva1"));
                    aluno.setNotaProva2(rs.getDouble("notaProva2"));
                    alunos.add(aluno);
                }

                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar alunos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return alunos;
    }
}
