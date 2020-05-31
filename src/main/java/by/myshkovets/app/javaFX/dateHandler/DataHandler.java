package by.myshkovets.app.javaFX.dateHandler;

import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;

import java.util.List;

public interface DataHandler<T> {

    void add(StatisticsDataProperty data);

    void update(int id, StatisticsDataProperty data);

    void delete(int id);

    List<T> find(StatisticsDataProperty data);
}
