package by.myshkovets.app.javaFX.comparator;

import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;

import java.util.Comparator;

public class StatisticsDataPropertyComparator implements Comparator<StatisticsDataProperty> {
    @Override
    public int compare(StatisticsDataProperty o1, StatisticsDataProperty o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
