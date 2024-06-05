import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection GeraConexao() {
        Connection conexao = null;
        try {
            String url = "jdbc:mysql://localhost:3306/teste2";
            String usuario = "root";
            String senha = "1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao tentar conectar: " + e.getMessage());
            e.printStackTrace();
            conexao = null;
        }
        return conexao;
    }
}
