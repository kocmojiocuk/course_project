package by.myshkovets.app.javaFX.dao;

import by.myshkovets.app.javaFX.dao.accountDAO.AccountDAO;
import by.myshkovets.app.javaFX.dao.accountDAO.AccountDAOImpl;
import by.myshkovets.app.javaFX.dao.goalsDAO.GoalsDAO;
import by.myshkovets.app.javaFX.dao.goalsDAO.GoalsDAOImpl;
import by.myshkovets.app.javaFX.dao.goalsDAO.statistics.GoalsStatisticsDAOImpl;
import by.myshkovets.app.javaFX.dao.tableDateDAO.TableDateDAO;
import by.myshkovets.app.javaFX.dao.tableDateDAO.TableDateDAOImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorDB {

    private static Connection connection;
    private static AccountDAO accountDAO;
    private static TableDateDAO tableDateDAO;
    private static GoalsDAO goalsDAO;
    private static GoalsStatisticsDAOImpl goalsStatisticsDAO;
    private static final ConnectorDB connectorDB = new ConnectorDB();

    private ConnectorDB(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("properties" + File.separator + "db.properties"));
            String URL = properties.getProperty("db.host");
            String login = properties.getProperty("db.login");
            String password = properties.getProperty("db.password");
            connection = DriverManager.getConnection(URL, login, password);

            accountDAO = new AccountDAOImpl();
            tableDateDAO = new TableDateDAOImpl();
            goalsDAO = new GoalsDAOImpl();
            goalsStatisticsDAO = new GoalsStatisticsDAOImpl();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection(){
        return connection;
    }

    public static AccountDAO getAccountDAO() {
        return accountDAO;
    }
    public static TableDateDAO getTableDateDAO() {
        return tableDateDAO;
    }
    public static GoalsDAO getGoalsDAO() { return goalsDAO; }
    public static GoalsStatisticsDAOImpl getGoalsStatisticsDAO() { return goalsStatisticsDAO; }
}
