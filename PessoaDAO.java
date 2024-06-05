import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    private static List<Pessoa> pessoas = new ArrayList<>();

    public void cadastrarPessoa(Pessoa pessoa) {
        Connection conexao = Conexao.GeraConexao();
        if (conexao != null) {
            try {
                String sql = "INSERT INTO Pessoas (nome, cpf, tipo) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, pessoa.getNome());
                stmt.setString(2, pessoa.getCpf());
                stmt.setString(3, pessoa instanceof Aluno ? "Aluno" : "Funcionario");
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int pessoaId = generatedKeys.getInt(1);

                    if (pessoa instanceof Aluno) {
                        String alunoSql = "INSERT INTO Alunos (id) VALUES (?)";
                        PreparedStatement alunoStmt = conexao.prepareStatement(alunoSql);
                        alunoStmt.setInt(1, pessoaId);
                        alunoStmt.executeUpdate();
                        alunoStmt.close();
                    } else if (pessoa instanceof Funcionario) {
                        String funcionarioSql = "INSERT INTO Funcionarios (id, cargo) VALUES (?, ?)";
                        PreparedStatement funcionarioStmt = conexao.prepareStatement(funcionarioSql);
                        funcionarioStmt.setInt(1, pessoaId);
                        funcionarioStmt.setString(2, ((Funcionario) pessoa).getCargo());
                        funcionarioStmt.executeUpdate();
                        funcionarioStmt.close();
                    }
                }

                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar pessoa: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Pessoa encontrarPessoaPorCpf(String cpf) {
        Connection conexao = Conexao.GeraConexao();
        Pessoa pessoa = null;
        if (conexao != null) {
            try {
                String sql = "SELECT id, nome, tipo FROM Pessoas WHERE cpf = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String tipo = rs.getString("tipo");
                    if ("Aluno".equals(tipo)) {
                        pessoa = new Aluno(nome, cpf);
                    } else if ("Funcionario".equals(tipo)) {
                        pessoa = new Funcionario(nome, cpf, null);
                    }
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao encontrar pessoa: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pessoa;
    }

    public List<Pessoa> listarPessoas() {
        Connection conexao = Conexao.GeraConexao();
        List<Pessoa> pessoas = new ArrayList<>();
        if (conexao != null) {
            try {
                String sql = "SELECT p.id, p.nome, p.cpf, p.tipo, a.notaProva1, a.notaProva2, f.cargo " +
                        "FROM Pessoas p " +
                        "LEFT JOIN Alunos a ON p.id = a.id " +
                        "LEFT JOIN Funcionarios f ON p.id = f.id";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String tipo = rs.getString("tipo");
                    if ("Aluno".equals(tipo)) {
                        Aluno aluno = new Aluno(nome, cpf);
                        aluno.setNotaProva1(rs.getDouble("notaProva1"));
                        aluno.setNotaProva2(rs.getDouble("notaProva2"));
                        pessoas.add(aluno);
                    } else if ("Funcionario".equals(tipo)) {
                        String cargo = rs.getString("cargo");
                        Funcionario funcionario = new Funcionario(nome, cpf, cargo);
                        pessoas.add(funcionario);
                    }
                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar pessoas: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pessoas;
    }
}
