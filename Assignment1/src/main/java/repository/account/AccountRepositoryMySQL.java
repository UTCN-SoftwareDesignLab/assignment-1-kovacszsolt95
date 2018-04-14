package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public List<Account> findByOwnerID(Long client_id) {
        List<Account> ownedAccounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where client_id=" + client_id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                ownedAccounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedAccounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?,?)");
            insertStatement.setLong(1, account.getOwnerID());
            insertStatement.setString(2, account.getType());
            insertStatement.setLong(3, account.getBalance());
            insertStatement.setDate(4, new Date(account.getDateOfCreation().getTime()));
            insertStatement.executeUpdate();
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
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateBalance(Long accountID, Long newBalance) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            updateStatement.setLong(1, newBalance);
            updateStatement.setLong(2, accountID);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeByID(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id =" + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        AccountBuilder accountBuilder = new AccountBuilder();
        accountBuilder.setId(rs.getLong("id"));
        accountBuilder.setOwnerId(rs.getLong("client_id"));
        accountBuilder.setType(rs.getString("type"));
        accountBuilder.setBalance(rs.getLong("balance"));
        accountBuilder.setDateOfCreation(new Date(rs.getDate("dateOfCreation").getTime()));
        return accountBuilder.build();
    }

}
