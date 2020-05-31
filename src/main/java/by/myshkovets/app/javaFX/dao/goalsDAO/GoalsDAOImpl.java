package by.myshkovets.app.javaFX.dao.goalsDAO;

import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.dao.ConnectorDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalsDAOImpl implements GoalsDAO {

    private final static GoalsDAO goalsDAO = ConnectorDB.getGoalsDAO();
    private Connection connection = ConnectorDB.getConnection();

    //SQL
    private static final String SQL_UPDATE_GOALS = "UPDATE goals SET goal = ? WHERE id = ?";
    //SQL
    private static final String SQL_DELETE_GOALS = "DELETE FROM goals WHERE id = ?";
    //SQL
    private static final String SQL_SELECT_GOALS = "SELECT * FROM goals";
    //SQL
    private static final String SQL_INSERT_GOALS = "INSERT INTO goals(goal) VALUES (?)";
    //SQL
    private static final String SQL_LAST_INDEX_GOALS = "SELECT id FROM goals WHERE id = (select MAX(id) FROM goals)";


    @Override
    public List<Goal> findAll() {
        List<Goal> dataList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_GOALS);

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String goal = resultSet.getString(2);
                List<StatisticsData> statistics = ConnectorDB.getGoalsStatisticsDAO().getStatistics(id);
                Goal data = new Goal(id, goal, statistics);
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
    public boolean delete(Goal entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_GOALS);
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            ConnectorDB.getGoalsStatisticsDAO().delete(entity);
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int create(Goal entity) {

        int id = 0;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SQL_INSERT_GOALS);
            statement.setString(1, entity.getGoal());
            statement.executeUpdate();
            resultSet = statement.executeQuery(SQL_LAST_INDEX_GOALS);
            if(resultSet.next()){
                id = resultSet.getInt(1);
                entity.setId(id);
            }
            ConnectorDB.getGoalsStatisticsDAO().create(entity);
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean update(Goal entity) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_GOALS);
            statement.setString(1, entity.getGoal());
            statement.setInt(2, entity.getId());
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
