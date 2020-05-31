package by.myshkovets.app.javaFX.dao.tableDateDAO;

import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.parser.dateParser.DateParser;
import by.myshkovets.app.javaFX.dao.ConnectorDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDateDAOImpl implements TableDateDAO{

    private static TableDateDAO tableDateDAO = ConnectorDB.getTableDateDAO();
    private Connection connection = ConnectorDB.getConnection();

    //SQL
    private static final String SQL_LAST_INDEX_STATISTICS = "SELECT id FROM statistics WHERE id = (select MAX(id) from statistics)";
    //SQL
    private static final String SQL_SELECT_STATISTICS = "SELECT * FROM statistics";
    //SQL
    private static final String SQL_INSERT_STATISTICS = "INSERT INTO statistics(operation, content, quantity, cash, data) VALUES (?,?,?,?,?)";
    //SQL
    private static final String SQL_UPDATE_STATISTICS = "UPDATE statistics SET operation = ?, content = ?, quantity = ?, cash = ?, data = ? WHERE id = ?";
    //SQL
    private static final String SQL_DELETE_STATISTICS = "DELETE FROM statistics WHERE id = ?";
    //SQL
    private static final String SQL_SELECT_BY_ID_STATISTICS = "SELECT * FROM statistics WHERE id = ?";



    @Override
    public List<StatisticsData> findAll() {
        List<StatisticsData> dataList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_STATISTICS);

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String operation = resultSet.getString(2);
                String content = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                int cash = resultSet.getInt(5);
                String date = resultSet.getString(6);

                StatisticsData data = new StatisticsData(id, operation, content, String.valueOf(quantity), String.valueOf(cash), DateParser.parse(date));
                dataList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);
        }
        return dataList;
    }


    @Override
    public StatisticsData find(int id) {
        Statement statement = null;
        ResultSet resultSet;
        StatisticsData data = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM statistics WHERE id = " + id);

            if (resultSet.next()){

                String operation = resultSet.getString(2);
                String content = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                int cash = resultSet.getInt(5);
                String date = resultSet.getString(6);
                data = new StatisticsData(id, operation, content, String.valueOf(quantity), String.valueOf(cash), DateParser.parse(date));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);
        }
        return data;
    }

    @Override
    public boolean delete(List<StatisticsData> entities) {
        PreparedStatement statement = null;
        for(StatisticsData data: entities){
            try {
                statement = connection.prepareStatement(SQL_DELETE_STATISTICS);
                statement.setInt(1, data.getId());
                statement.executeUpdate();
            } catch (SQLException e)  {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean delete(StatisticsData entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_STATISTICS);
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int create(StatisticsData entity) {

        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SQL_INSERT_STATISTICS);
            statement.setString(1, entity.getOperation());
            statement.setString(2, entity.getContent());
            statement.setInt(3, Integer.valueOf(entity.getQuantity()));
            statement.setInt(4, Integer.valueOf(entity.getCash()));
            statement.setString(5, entity.getDateToString());
            statement.executeUpdate();
            resultSet = statement.executeQuery(SQL_LAST_INDEX_STATISTICS);
            if(resultSet.next()){
               entity.setId(resultSet.getInt(1));
            }

        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return entity.getId();
    }

    @Override
    public boolean update(StatisticsData entity) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_STATISTICS);
            statement.setInt(6, entity.getId());
            statement.setString(5, entity.getDateToString());
            statement.setInt(4, Integer.valueOf(entity.getCash()));
            statement.setInt(3, Integer.valueOf(entity.getQuantity()));
            statement.setString(2, entity.getContent());
            statement.setString(1, entity.getOperation());
            statement.executeUpdate();
            return true;
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean closeStatement(Statement statement) {
        return false;
    }
}
