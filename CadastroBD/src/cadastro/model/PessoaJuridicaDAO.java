/* 
    Autor: Wallace Tavares

 */
package cadastro.model;
import java.sql.Statement;
import cadastrobd.model.PessoaJuridica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PessoaJuridicaDAO {
    private final ConectorBD conectorBD;
    public PessoaJuridicaDAO() {
        this.conectorBD = new ConectorBD();
    }
    public PessoaJuridica getPessoa(int id) {
        PessoaJuridica pessoaJuridica = null;
        Connection connection = null;
        PreparedStatement statementPessoa = null;
        PreparedStatement statementPessoaJuridica = null;
        ResultSet resultSetPessoa = null;
        ResultSet resultSetPessoaJuridica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoa = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
            statementPessoa = connection.prepareStatement(sqlPessoa);
            statementPessoa.setInt(1, id);
            resultSetPessoa = statementPessoa.executeQuery();
            if (resultSetPessoa.next()) {
                pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSetPessoa.getInt("idPessoa"));
                pessoaJuridica.setNome(resultSetPessoa.getString("nome"));
                pessoaJuridica.setLogradouro(resultSetPessoa.getString("logradouro"));
                pessoaJuridica.setCidade(resultSetPessoa.getString("cidade"));
                pessoaJuridica.setEstado(resultSetPessoa.getString("estado"));
                pessoaJuridica.setTelefone(resultSetPessoa.getString("telefone"));
                pessoaJuridica.setEmail(resultSetPessoa.getString("email"));
            }
            String sqlPessoaJuridica = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
            statementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
            statementPessoaJuridica.setInt(1, id);
            resultSetPessoaJuridica = statementPessoaJuridica.executeQuery();
            if (resultSetPessoaJuridica.next()) {
                pessoaJuridica.setCnpj(resultSetPessoaJuridica.getString("CNPJ"));
            }
        } catch (SQLException e) {
        } finally {
            conectorBD.close(resultSetPessoaJuridica);
            conectorBD.close(statementPessoaJuridica);
            conectorBD.close(resultSetPessoa);
            conectorBD.close(statementPessoa);
            conectorBD.close(connection);
        }
        return pessoaJuridica;
    }
    public List<PessoaJuridica> listarTodasPessoasJuridicas() {
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statementPessoa = null;
        PreparedStatement statementPessoaJuridica = null;
        ResultSet resultSetPessoa = null;
        ResultSet resultSetPessoaJuridica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoa = "SELECT * FROM PessoaJuridica";
            statementPessoa = connection.prepareStatement(sqlPessoa);
            resultSetPessoa = statementPessoa.executeQuery();
            while (resultSetPessoa.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSetPessoa.getInt("idPessoa"));
                pessoaJuridica.setNome(resultSetPessoa.getString("nome"));
                pessoaJuridica.setLogradouro(resultSetPessoa.getString("logradouro"));
                pessoaJuridica.setCidade(resultSetPessoa.getString("cidade"));
                pessoaJuridica.setEstado(resultSetPessoa.getString("estado"));
                pessoaJuridica.setTelefone(resultSetPessoa.getString("telefone"));
                pessoaJuridica.setEmail(resultSetPessoa.getString("email"));
                String sqlPessoaJuridica = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
                statementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
                statementPessoaJuridica.setInt(1, pessoaJuridica.getId());
                resultSetPessoaJuridica = statementPessoaJuridica.executeQuery();
                if (resultSetPessoaJuridica.next()) {
                    pessoaJuridica.setCnpj(resultSetPessoaJuridica.getString("CNPJ"));
                }
                pessoasJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
        } finally {
            conectorBD.close(resultSetPessoaJuridica);
            conectorBD.close(statementPessoaJuridica);
            conectorBD.close(resultSetPessoa);
            conectorBD.close(statementPessoa);
            conectorBD.close(connection);
        }
        return pessoasJuridicas;
    }
    public void incluir(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 
            String sqlPessoa = 
                    "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pessoaJuridica.getNome());
            statement.setString(2, pessoaJuridica.getLogradouro());
            statement.setString(3, pessoaJuridica.getCidade());
            statement.setString(4, pessoaJuridica.getEstado());
            statement.setString(5, pessoaJuridica.getTelefone());
            statement.setString(6, pessoaJuridica.getEmail());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int idPessoa = 0;
            if (generatedKeys.next()) {
                idPessoa = generatedKeys.getInt(1);
            }
            String sqlPessoaJuridica =
                    "INSERT INTO PessoaJuridica (idPessoa, nome, CNPJ, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlPessoaJuridica);
            statement.setInt(1, idPessoa);
            statement.setString(2, pessoaJuridica.getNome());
            statement.setString(3, pessoaJuridica.getCnpj());
            statement.setString(4, pessoaJuridica.getLogradouro());
            statement.setString(5, pessoaJuridica.getCidade());
            statement.setString(6, pessoaJuridica.getEstado());
            statement.setString(7, pessoaJuridica.getTelefone());
            statement.setString(8, pessoaJuridica.getEmail());
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
    public void alterar(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false);
            String sqlVerificarExistencia = 
                    "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaJuridica.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlPessoaJuridica = 
                        "UPDATE PessoaJuridica SET nome = ?, CNPJ = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlPessoaJuridica);
                statement.setString(1, pessoaJuridica.getNome());
                statement.setString(2, pessoaJuridica.getCnpj());
                statement.setString(3, pessoaJuridica.getLogradouro());
                statement.setString(4, pessoaJuridica.getCidade());
                statement.setString(5, pessoaJuridica.getEstado());
                statement.setString(6, pessoaJuridica.getTelefone());
                statement.setString(7, pessoaJuridica.getEmail());
                statement.setInt(8, pessoaJuridica.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Nenhuma linha foi atualizada. Verifique se o ID da PessoaJuridica está correto.");
                }
                connection.commit(); 
            } else {
                throw new SQLException("A PessoaJuridica com o ID especificado não existe no banco de dados.");
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
            String sqlVerificarExistencia = 
                    "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlExcluirPessoaJuridica =
                        "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlExcluirPessoaJuridica);
                statement.setInt(1, id);
                statement.executeUpdate();
                String sqlExcluirPessoa = 
                        "DELETE FROM Pessoa WHERE idPessoa = ?";
                statement = connection.prepareStatement(sqlExcluirPessoa);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit(); 
                System.out.println("Pessoa jurídica excluída com sucesso.");
            } else {
                System.out.println("A pessoa com o ID especificado não foi encontrada.");
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
    public PessoaJuridica buscarPorId(int idPessoa) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PessoaJuridica pessoaJuridica = null;
        try {
            connection = conectorBD.getConnection();
            String sql = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idPessoa);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("idPessoa");
                String nome = resultSet.getString("nome");
                String cnpj = resultSet.getString("CNPJ");
                String logradouro = resultSet.getString("logradouro");
                String cidade = resultSet.getString("cidade");
                String estado = resultSet.getString("estado");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(id);
                pessoaJuridica.setNome(nome);
                pessoaJuridica.setCnpj(cnpj);
                pessoaJuridica.setLogradouro(logradouro);
                pessoaJuridica.setCidade(cidade);
                pessoaJuridica.setEstado(estado);
                pessoaJuridica.setTelefone(telefone);
                pessoaJuridica.setEmail(email);
            }
        } catch (SQLException e) {
        } finally {
        }
        return pessoaJuridica;
    }
    public List<PessoaJuridica> getPessoasJuridicasPorId(int id) {
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statementPessoaJuridica = null;
        ResultSet resultSetPessoaJuridica = null;
        try {
            connection = conectorBD.getConnection();
            String sqlPessoaJuridica = "SELECT * FROM PessoaJuridica"; // Verifique se o nome da tabela está correto
            statementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
            resultSetPessoaJuridica = statementPessoaJuridica.executeQuery();
            while (resultSetPessoaJuridica.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSetPessoaJuridica.getInt("idPessoaJuridica"));
                pessoaJuridica.setNome(resultSetPessoaJuridica.getString("nome"));
                pessoaJuridica.setCnpj(resultSetPessoaJuridica.getString("CNPJ"));
                pessoaJuridica.setLogradouro(resultSetPessoaJuridica.getString("logradouro"));
                pessoaJuridica.setCidade(resultSetPessoaJuridica.getString("cidade"));
                pessoaJuridica.setEstado(resultSetPessoaJuridica.getString("estado"));
                pessoaJuridica.setTelefone(resultSetPessoaJuridica.getString("telefone"));
                pessoaJuridica.setEmail(resultSetPessoaJuridica.getString("email"));
                pessoasJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
        } finally {
            conectorBD.close(resultSetPessoaJuridica);
            conectorBD.close(statementPessoaJuridica);
            conectorBD.close(connection);
        }
        return pessoasJuridicas;
    }
}
