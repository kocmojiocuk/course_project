package by.myshkovets.app.javaFX.dao.goalsDAO.statistics;

import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.dao.DAO;

import java.util.List;

public interface GoalsStatistics extends DAO<Goal> {

    List<StatisticsData> getStatistics(int id);
}
