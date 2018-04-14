package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public Long findIDbyCNP(String CNP) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where cnp=" + CNP;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return rs.getLong("id");
            } else {
                throw new EntityNotFoundException(null, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(null, Client.class.getSimpleName());
        }
    }

    @Override
    public Notification<Client> findById(Long id) {
        Notification<Client> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            findByIdNotification.setResult(getClientFromResultSet(rs));
            return findByIdNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findByIdNotification.addError("Could not find ID");
            return findByIdNotification;
        }
    }


    public Boolean isCNPFree(String CNP) {
        Notification<Client> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select COUNT(cnp) from client where cnp=" + CNP;
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("count(cnp)") == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?,?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getPersonalNumericCode());
            insertStatement.setString(3, client.getAdress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClient(Client client) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE client SET name = ?, cnp = ?, adress=? WHERE id = ?");
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getPersonalNumericCode());
            updateStatement.setString(3, client.getAdress());
            updateStatement.setLong(4, client.getId());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeByID(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id =" + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setId(rs.getLong("id"));
        clientBuilder.setName(rs.getString("name"));
        clientBuilder.setPersonalNumericCode(rs.getString("cnp"));
        clientBuilder.setAdress(rs.getString("adress"));
        return clientBuilder.build();
    }

}
