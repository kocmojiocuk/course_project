package by.myshkovets.app.javaFX.entity.tables;

import by.myshkovets.app.javaFX.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Goal extends Entity {
    private String goal;
    private String statistics;
    transient private List<StatisticsDataProperty> statisticsList;
    private List<StatisticsData> statisticsListBuffer;

    public Goal(String goal, List<StatisticsDataProperty> statistics) {
        super(0);
        this.goal = goal;
        this.statisticsList = new ArrayList<>(statistics);
        this.statistics = statistics.toString();
        statisticsListBuffer = new ArrayList<>();
        for(StatisticsDataProperty data: statistics){
            statisticsListBuffer.add(new StatisticsData(data));
        }
    }

    public Goal(int id, String goal, List<StatisticsData> statistics) {
        super(id);
        this.goal = goal;
        this.statisticsListBuffer = new ArrayList<>(statistics);
        this.statistics = statistics.toString();
    }

    public Goal(int id, String goal, String statistics) {
        super(id);
        this.goal = goal;
        this.statistics = statistics;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStatistics() {
        return statistics;
    }
    public List<StatisticsDataProperty> getStatisticsList() {
        if(statisticsList == null){
            statisticsList = new ArrayList<>();
            for(StatisticsData data: statisticsListBuffer){
                if(data == null){
                }else{

                    statisticsList.add(new StatisticsDataProperty(data));
                }
            }
        }
        return statisticsList;
    }
    public List<StatisticsData> getStatisticsListBuffer() {
        return statisticsListBuffer;
    }
    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    @Override
    public String toString() {
        return goal;
    }
}
