package by.myshkovets.app.javaFX.dao.tableDateDAO;

import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.dao.DAO;

import java.util.List;

public interface TableDateDAO extends DAO<StatisticsData> {
    StatisticsData find(int id);
    boolean delete(List<StatisticsData> entities);



}
