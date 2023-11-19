/* 
    Autor: Wallace Tavares
 */
package cadastro.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectorBD {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Loja;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "2324181203";
        return DriverManager.getConnection(url, user, password);
    }

    public PreparedStatement getPrepared(String sql) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(sql);
    }

    public ResultSet getSelect(String sql) throws SQLException {
        Statement statement = getConnection().createStatement();
        return statement.executeQuery(sql);
    }

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
        }
    }

    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
        }
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
        }
    }

    Statement getStatement() {
        return null;
    }

}
