package by.myshkovets.app.javaFX.dao.goalsDAO.statistics;

import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.dao.ConnectorDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalsStatisticsDAOImpl implements GoalsStatistics {
    private final static GoalsStatisticsDAOImpl goalsStatisticsDAO = ConnectorDB.getGoalsStatisticsDAO();
    private Connection connection = ConnectorDB.getConnection();
    //SQL
    private static final String SQL_DELETE_GOALS_STATISTICS = "DELETE FROM goals_statistics WHERE id_goals = ?";
    //SQL
    private static final String SQL_INSERT_GOALS_STATISTICS = "INSERT INTO goals_statistics(id_goals, id_statistics) VALUES (?,?)";
    //SQL
    private static final String SQL_GET_STATISTICS_GOALS_STATISTICS = "SELECT id_statistics FROM goals_statistics WHERE id_goals = ?";

    @Override
    public List<Goal> findAll() {
        return null;
    }

    @Override
    public boolean delete(Goal entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_GOALS_STATISTICS);
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int create(Goal entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_GOALS_STATISTICS);
            for(StatisticsData data: entity.getStatisticsListBuffer()) {
                statement.setInt(2, data.getId());
                statement.setInt(1, entity.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean update(Goal entity) {
        return false;
    }

    @Override
    public boolean closeStatement(Statement statement) {
        return false;
    }


    @Override
    public List<StatisticsData> getStatistics(int id) {
        List<StatisticsData> dataList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SQL_GET_STATISTICS_GOALS_STATISTICS);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int statisticsId = resultSet.getInt("id_statistics");
                StatisticsData statisticsData = ConnectorDB.getTableDateDAO().find(statisticsId);
                dataList.add(statisticsData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);
        }
        return dataList;
    }
}
