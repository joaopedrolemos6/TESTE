import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoBd {
    public static void main(String[] args) throws SQLException{
        Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/teste2","root","1234");

        conexao.close();
    }
}