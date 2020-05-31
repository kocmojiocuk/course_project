package by.myshkovets.app.javaFX.dao.accountDAO;

import by.myshkovets.app.javaFX.entity.account.Account;
import by.myshkovets.app.javaFX.entity.account.Role;
import by.myshkovets.app.javaFX.dao.ConnectorDB;

import java.sql.*;
import java.util.List;
import java.util.Optional;


public class AccountDAOImpl implements AccountDAO {

    private final static AccountDAO accountDAO = ConnectorDB.getAccountDAO();
    private Connection connection = ConnectorDB.getConnection();

    //SQL
    private static final String SQL_SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";
    //SQL
    private static final String SQL_SELECT_ACCOUNT = "SELECT * FROM accounts WHERE login = ? AND password = ?";
    //SQL
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM accounts WHERE login = ?";
    //SQL
    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO accounts (name, surname, login, password, role, mail) VALUES (?, ?, ?, ?, ?, ?)";





    @Override
    public boolean isExist(Account account) {
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SQL_SELECT_LOGIN);
            statement.setString(1, account.getLogin());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Account> getAccount(Account account){
        PreparedStatement statement = null;
        ResultSet resultSet;
        Account fullAccount = null;
        try {
             statement = connection.prepareStatement(SQL_SELECT_ACCOUNT);
             statement.setString(1, account.getLogin());
             statement.setString(2, account.getPassword());

             resultSet = statement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                Role role = Role.valueOf(resultSet.getString("role").toUpperCase());
                String mail = resultSet.getString("mail");

                fullAccount = Account.builder()
                        .name(name)
                        .surname(surname)
                        .mail(mail)
                        .login(login)
                        .password(password)
                        .role(role)
                        .build();


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);
        }
        return Optional.ofNullable(fullAccount);
    }


    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public boolean delete(Account account) {
        return false;
    }

    @Override
    public int create(Account account) {
        PreparedStatement statement = null;
        if(isExist(account)){
            return 0;
        }
        try {
            statement = connection.prepareStatement(SQL_INSERT_ACCOUNT);
            statement.setString(1, account.getName());
            statement.setString(2, account.getSurname());
            statement.setString(3, account.getLogin());
            statement.setString(4, account.getPassword());
            statement.setString(5, account.getRole().toString());
            statement.setString(6, account.getMial());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Account entity) {
        return false;
    }

    @Override
    public boolean closeStatement(Statement statement) {
        try{
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
