/* 
    Autor: Wallace Tavares
 */
package cadastro.model;
import java.sql.Statement;
import cadastrobd.model.PessoaFisica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private final ConectorBD conectorBD;
    public PessoaFisicaDAO() {
        this.conectorBD = new ConectorBD();
    }
    public PessoaFisica getPessoa(int id) {
        PessoaFisica pessoaFisica = null;
        Connection connection = null;
        PreparedStatement statementPessoa = null;
        PreparedStatement statementPessoaFisica = null;
        ResultSet resultSetPessoa = null;
        ResultSet resultSetPessoaFisica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoa = "SELECT * FROM Pessoa WHERE idPessoa = ?";
            statementPessoa = connection.prepareStatement(sqlPessoa);
            statementPessoa.setInt(1, id);
            resultSetPessoa = statementPessoa.executeQuery();
            if (resultSetPessoa.next()) {
                pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSetPessoa.getInt("idPessoa"));
                pessoaFisica.setNome(resultSetPessoa.getString("nome"));
                pessoaFisica.setLogradouro(resultSetPessoa.getString("logradouro"));
                pessoaFisica.setCidade(resultSetPessoa.getString("cidade"));
                pessoaFisica.setEstado(resultSetPessoa.getString("estado"));
                pessoaFisica.setTelefone(resultSetPessoa.getString("telefone"));
                pessoaFisica.setEmail(resultSetPessoa.getString("email"));}
            String sqlPessoaFisica = "SELECT * FROM PessoaFisica WHERE idPessoa = ?";
            statementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            statementPessoaFisica.setInt(1, id);
            resultSetPessoaFisica = statementPessoaFisica.executeQuery();
            if (resultSetPessoaFisica.next()) {
                pessoaFisica.setCpf(resultSetPessoaFisica.getString("CPF"));
            }
        } catch (SQLException e) {
        } finally {
            conectorBD.close(resultSetPessoaFisica);
            conectorBD.close(statementPessoaFisica);
            conectorBD.close(resultSetPessoa);
            conectorBD.close(statementPessoa);
            conectorBD.close(connection);
        }
        return pessoaFisica;
    }
    public List<PessoaFisica> listarTodasPessoasFisicas() {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statementPessoa = null;
        PreparedStatement statementPessoaFisica = null;
        ResultSet resultSetPessoa = null;
        ResultSet resultSetPessoaFisica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoa = "SELECT * FROM PessoaFisica";
            statementPessoa = connection.prepareStatement(sqlPessoa);
            resultSetPessoa = statementPessoa.executeQuery();
            while (resultSetPessoa.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSetPessoa.getInt("idPessoa"));
                pessoaFisica.setNome(resultSetPessoa.getString("nome"));
                pessoaFisica.setLogradouro(resultSetPessoa.getString("logradouro"));
                pessoaFisica.setCidade(resultSetPessoa.getString("cidade"));
                pessoaFisica.setEstado(resultSetPessoa.getString("estado"));
                pessoaFisica.setTelefone(resultSetPessoa.getString("telefone"));
                pessoaFisica.setEmail(resultSetPessoa.getString("email"));
                String sqlPessoaFisica = "SELECT * FROM PessoaFisica WHERE idPessoa = ?";
                statementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
                statementPessoaFisica.setInt(1, pessoaFisica.getId());
                resultSetPessoaFisica = statementPessoaFisica.executeQuery();

                if (resultSetPessoaFisica.next()) {
                    pessoaFisica.setCpf(resultSetPessoaFisica.getString("CPF"));
                }
                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
        } finally {
            conectorBD.close(resultSetPessoaFisica);
            conectorBD.close(statementPessoaFisica);
            conectorBD.close(resultSetPessoa);
            conectorBD.close(statementPessoa);
            conectorBD.close(connection);
        }
        return pessoasFisicas;
    }
    public void incluir(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 
            String sqlPessoa = 
                    "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pessoaFisica.getNome());
            statement.setString(2, pessoaFisica.getLogradouro());
            statement.setString(3, pessoaFisica.getCidade());
            statement.setString(4, pessoaFisica.getEstado());
            statement.setString(5, pessoaFisica.getTelefone());
            statement.setString(6, pessoaFisica.getEmail());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int idPessoa = 0;
            if (generatedKeys.next()) {
                idPessoa = generatedKeys.getInt(1);
            }
            String sqlPessoaFisica = 
                    "INSERT INTO PessoaFisica (idPessoa, nome, CPF, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlPessoaFisica);
            statement.setInt(1, idPessoa);
            statement.setString(2, pessoaFisica.getNome());
            statement.setString(3, pessoaFisica.getCpf());
            statement.setString(4, pessoaFisica.getLogradouro());
            statement.setString(5, pessoaFisica.getCidade());
            statement.setString(6, pessoaFisica.getEstado());
            statement.setString(7, pessoaFisica.getTelefone());
            statement.setString(8, pessoaFisica.getEmail());
            statement.executeUpdate();
            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    public void alterar(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 
            String sqlVerificarExistencia =
                    "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaFisica.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlPessoaFisica = 
                        "UPDATE PessoaFisica SET nome = ?, cpf = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlPessoaFisica);
                statement.setString(1, pessoaFisica.getNome());
                statement.setString(2, pessoaFisica.getCpf());
                statement.setString(3, pessoaFisica.getLogradouro());
                statement.setString(4, pessoaFisica.getCidade());
                statement.setString(5, pessoaFisica.getEstado());
                statement.setString(6, pessoaFisica.getTelefone());
                statement.setString(7, pessoaFisica.getEmail());
                statement.setInt(8, pessoaFisica.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Nenhuma linha foi atualizada. Verifique se o ID da PessoaFisica está correto.");
                }
                connection.commit(); 
            } else {
                throw new SQLException("A PessoaFisica com o ID fornecido não existe no banco de dados.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    public void excluir(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 
            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlExcluirPessoaFisica = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlExcluirPessoaFisica);
                statement.setInt(1, id);
                statement.executeUpdate();
                String sqlExcluirPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlExcluirPessoa);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit(); 
                System.out.println("Pessoa física excluída.");
            } else {
                System.out.println("A pessoa com o ID fornecido não foi encontrada.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    public PessoaFisica buscarPorId(int idPessoa) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PessoaFisica pessoaFisica = null;
        try {
            connection = conectorBD.getConnection();
            String sql = "SELECT * FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idPessoa);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("idPessoaFisica");
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("CPF");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String estado = resultSet.getString("estado");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(id);
                pessoaFisica.setNome(nome);
                pessoaFisica.setCpf(cpf);
                pessoaFisica.setLogradouro(logradouro);
                pessoaFisica.setCidade(cidade);
                pessoaFisica.setEstado(estado);
                pessoaFisica.setTelefone(telefone);
                pessoaFisica.setEmail(email);
            }
        } catch (SQLException e) {
        } finally {
        }
        return pessoaFisica;
    }
    public List<PessoaFisica> getPessoasFisicasPorId(int id) {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statementPessoaFisica = null;
        ResultSet resultSetPessoaFisica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoaFisica = "SELECT * FROM PessoaFisica"; 
            statementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            resultSetPessoaFisica = statementPessoaFisica.executeQuery();
            while (resultSetPessoaFisica.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSetPessoaFisica.getInt("idPessoaFisica")); 
                pessoaFisica.setNome(resultSetPessoaFisica.getString("nome")); 
                pessoaFisica.setCpf(resultSetPessoaFisica.getString("CPF")); 
                pessoaFisica.setLogradouro(resultSetPessoaFisica.getString("logradouro")); 
                pessoaFisica.setCidade(resultSetPessoaFisica.getString("cidade")); 
                pessoaFisica.setEstado(resultSetPessoaFisica.getString("estado"));
                pessoaFisica.setTelefone(resultSetPessoaFisica.getString("telefone"));
                pessoaFisica.setEmail(resultSetPessoaFisica.getString("email")); 
                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            conectorBD.close(resultSetPessoaFisica);
            conectorBD.close(statementPessoaFisica);
            conectorBD.close(connection);
        }
        return pessoasFisicas;
    }
}
