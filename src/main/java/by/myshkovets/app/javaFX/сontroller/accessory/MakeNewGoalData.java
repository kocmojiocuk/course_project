package by.myshkovets.app.javaFX.сontroller.accessory;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import by.myshkovets.app.javaFX.message.MessageType;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MakeNewGoalData {

    private Stage stage;
    private List<StatisticsDataProperty> statistics;
    private ObservableList<Label> dataLabelList;
    private ObservableList<Goal> dataList;



    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setStatistics(List<StatisticsDataProperty> statistics) {
        this.statistics = new ArrayList<>(statistics);
    }
    public void setDataLabelList(ObservableList<Label> dataLabelList) { this.dataLabelList = dataLabelList; }
    public void setDataList(ObservableList<Goal> dataList) {
        this.dataList = dataList;
    }


    @FXML
    private JFXTextArea textField;

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        String textGoal = textField.getText();
        if(textGoal != null && statistics != null){
            Goal goal = new Goal(textGoal, statistics);
            Label label = new Label();
            label.setText(goal.getGoal());
            label.setAccessibleText(goal.getStatistics());
            label.setStyle("-fx-background-color:  #8FBC8F");
            label.setPrefWidth(536);
            label.setPrefHeight(60);
            dataList.add(goal);
            dataLabelList.add(label);

            Connection.getInstance().sendMessage(MessageType.MAKE_NEW_GOAL);
            Connection.getInstance().readMessage();
            Connection.getInstance().sendMessage(goal);
            int id = (Integer) Connection.getInstance().readMessageObject();
            goal.setId(id);

        }
        stage.close();


    }



}
