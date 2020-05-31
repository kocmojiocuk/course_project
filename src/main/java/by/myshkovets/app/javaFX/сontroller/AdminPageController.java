package by.myshkovets.app.javaFX.сontroller;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.datapool.DataPool;
import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import by.myshkovets.app.javaFX.excel.worker.ExcelWorker;
import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.сontroller.accessory.AddNewStatisticsData;
import by.myshkovets.app.javaFX.сontroller.accessory.MakeNewGoalData;
import by.myshkovets.app.javaFX.сontroller.accessory.UpdateGoalData;
import by.myshkovets.app.javaFX.сontroller.accessory.UpdateStatisticsData;
import by.myshkovets.app.javaFX.сontroller.notification.UpdateDataNotificationController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


public class AdminPageController implements Initializable {


    private ObservableList<StatisticsDataProperty> statisticsDataList;
    private ObservableList<Label> goalsLabelList;
    private ObservableList<Goal> goalsList;
    private static StatisticsDataProperty currentStatisticsChoice;
    private static Label currentLabelChoice;
    private static Goal currentGoalChoice;
    private boolean isMakeReport;
    private Stage stage;
    private static String notification;


    public static StatisticsDataProperty getCurrentStatisticsChoice() {
        return currentStatisticsChoice;
    }

    public static Label getCurrentLabelChoice() {
        return currentLabelChoice;
    }

    public static Goal getCurrentGoalChoice() {
        return currentGoalChoice;
    }

    public static String getNotification() {
        return notification;
    }

    ObservableList<Label> convertToLabelList(ObservableList<Goal> data) {
        ObservableList<Label> goalsList = FXCollections.observableArrayList();
        for (Goal goal : data) {
            Label label = new Label();
            label.setPrefWidth(536);
            label.setPrefHeight(60);
            label.setText(goal.getGoal());
            label.setAccessibleText(goal.getStatistics());
            label.setStyle("-fx-background-color:  #8FBC8F");
            goalsList.add(label);
        }
        return goalsList;
    }


    @FXML
    private TableView<StatisticsDataProperty> table;
    @FXML
    private TableColumn<StatisticsDataProperty, String> operation;
    @FXML
    private TableColumn<StatisticsDataProperty, String> content;
    @FXML
    private TableColumn<StatisticsDataProperty, String> quantity;
    @FXML
    private TableColumn<StatisticsDataProperty, String> cash;
    @FXML
    private TableColumn<StatisticsDataProperty, String> date;
    @FXML
    private TableColumn<StatisticsDataProperty, JFXCheckBox> checker;
    @FXML
    private JFXListView<Control> listView;
    @FXML
    private JFXListView<Label> goalsListField;
    @FXML
    private AnchorPane buttonPane;
    @FXML
    private AnchorPane buttonPaneNext;

    @FXML
    private AnchorPane anchor;
    @FXML



    private Label updateGoal = new Label("изменить");
    private Label deleteGoal = new Label("удалить");


    private JFXTextField contentFind;
    private JFXTextField quantityFind;
    private JFXTextField cashFind;

    //TODO оповещение о пустой дате, "элемент добавлен"
    void add() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_new_statistics_data.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("ДОБАВЛЕНИЕ ПОЛЯ");
        AddNewStatisticsData controller = loader.getController();
        controller.setDataList(statisticsDataList);
        controller.setStage(stage);
        stage.showAndWait();
    }

    void update() {
        ObservableList<StatisticsDataProperty> checkBoxList = FXCollections.observableArrayList();
        StatisticsDataProperty upddateDate;
        for (StatisticsDataProperty data : statisticsDataList) {
            if (data.getChecker().isSelected()) {
                checkBoxList.add(data);
            }
        }
        if (checkBoxList.size() == 1) {
            currentStatisticsChoice = checkBoxList.get(0);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_statistics_data.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateStatisticsData controller = loader.getController();
            controller.setDataList(statisticsDataList);
            controller.setStage(stage);
            stage.setTitle("ИЗМЕНИТЬ ПОЛЕ");
            stage.showAndWait();

        } else {
            notification = "Выберете только одно поле.";
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_data_notification.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateDataNotificationController controller = loader.getController();
            controller.setStage(stage);
            stage.setResizable(false);
            stage.showAndWait();
        }
        deleteAllCheckBoxes();

    }

    void delete() {
        List<StatisticsData> statistics = new ArrayList<>();
        ObservableList<StatisticsDataProperty> observableList = FXCollections.observableArrayList();
        for (StatisticsDataProperty data : statisticsDataList) {
            if (data.getChecker().isSelected()) {
                statistics.add(new StatisticsData(data));
                observableList.add(data);
            }
        }
        if (!statistics.isEmpty()) {
            Connection.getInstance().sendMessage(MessageType.DELETE_STATISTICS_DATA);
            Connection.getInstance().readMessage();
            Connection.getInstance().sendMessage(statistics);

            statisticsDataList.removeAll(observableList);
        }else {
            notification = "Выберете хотя бы одно поле.";
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_data_notification.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateDataNotificationController controller = loader.getController();
            controller.setStage(stage);
            stage.setResizable(false);
            stage.showAndWait();
        }
        deleteAllCheckBoxes();
    }

    void find() {


        //listView.setExpanded(false);
        // listView.depthProperty().set(0);


        FilteredList<StatisticsDataProperty> filteredContent = new FilteredList<>(statisticsDataList, p -> true);
        contentFind.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredContent.setPredicate(line -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (line.getContent().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<StatisticsDataProperty> sortedContent = new SortedList<>(filteredContent);
        sortedContent.comparatorProperty().bind(table.comparatorProperty());


        FilteredList<StatisticsDataProperty> filteredQuantity = new FilteredList<>(sortedContent, p -> true);
        quantityFind.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredQuantity.setPredicate(line -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (newValue.contains("-")) {
                    String[] digits = newValue.split("-");
                    int one = Integer.valueOf(digits[0]), two;
                    if (digits.length == 2) {
                        two = Integer.valueOf(digits[1]);
                        int number = Integer.valueOf(line.getQuantity());
                        if (number > one && number < two) {
                            return true;
                        }
                        return false;
                    }
                } else if (line.getQuantity().equals(newValue))
                    return true;
                return false;
            });
        });

        SortedList<StatisticsDataProperty> sortedQuantity = new SortedList<>(filteredQuantity);
        sortedQuantity.comparatorProperty().bind(table.comparatorProperty());


        FilteredList<StatisticsDataProperty> filteredCash = new FilteredList<>(sortedQuantity, p -> true);
        cashFind.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCash.setPredicate(line -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (newValue.contains("-")) {
                    String[] digits = newValue.split("-");
                    int one = Integer.valueOf(digits[0]), two;
                    if (digits.length == 2) {
                        two = Integer.valueOf(digits[1]);
                        int number = Integer.valueOf(line.getQuantity());
                        if (number > one && number < two) {
                            return true;
                        }
                        return false;
                    }
                } else if (line.getCash().equals(newValue))
                    return true;
                return false;
            });
        });

        SortedList<StatisticsDataProperty> sortedCash = new SortedList<>(filteredCash);
        sortedCash.comparatorProperty().bind(table.comparatorProperty());


        table.setItems(sortedCash);

    }


    void makeReport() {
        List<StatisticsDataProperty> selectedItems = new ArrayList<>();
        for (StatisticsDataProperty data : statisticsDataList) {
            if (data.getChecker().isSelected())
                selectedItems.add(data);
        }

        if (selectedItems.isEmpty()) {

            notification = "Выберете хотя бы одно поле.";
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_data_notification.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateDataNotificationController controller = loader.getController();
            controller.setStage(stage);
            stage.setResizable(false);
            stage.showAndWait();

            isMakeReport = true;
            for (StatisticsDataProperty data : statisticsDataList) {
                data.getChecker().setOnMouseMoved(e -> {
                    if (isMakeReport) {
                        data.getChecker().setSelected(true);
                    }
                });
            }

        } else {

            ExcelWorker excelWorker = new ExcelWorker(stage, selectedItems);
            try {
                excelWorker.makeExcelReport();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isMakeReport = false;
            }

        }
        deleteAllCheckBoxes();

    }

    void deleteAllCheckBoxes() {
        for (StatisticsDataProperty data : statisticsDataList) {
            if (data.getChecker().isSelected()) {
                data.getChecker().setSelected(false);
            }
        }
    }

    void makeGoal() {
        List<StatisticsDataProperty> statistics = new ArrayList<>();
        for (StatisticsDataProperty data : statisticsDataList) {
            if (data.getChecker().isSelected()) {
                statistics.add(data);
            }
        }

        if(!statistics.isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/make_new_goal.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            MakeNewGoalData controller = loader.getController();
            controller.setStatistics(statistics);
            controller.setStage(stage);
            controller.setDataLabelList(goalsLabelList);
            controller.setDataList(goalsList);
            stage.showAndWait();


            deleteGoalOnAction();
            updateGoalOnAction();
        }else{
            notification = "Выберете хотя бы одно поле.";
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_data_notification.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateDataNotificationController controller = loader.getController();
            controller.setStage(stage);
            stage.setResizable(false);
            stage.showAndWait();
        }

        deleteAllCheckBoxes();
    }


    void makeViewListGoalEditor() {
        JFXListView<Label> popupList = new JFXListView<>();
        popupList.setPrefSize(120, 76);
        popupList.getItems().addAll(updateGoal, deleteGoal);

        JFXPopup popup = new JFXPopup(popupList);
        Iterator<Label> iterator = goalsListField.getItems().iterator();
        while (iterator.hasNext()) {
            Label labelBuffer = iterator.next();
            labelBuffer.setOnMouseClicked(e -> {
                labelBuffer.setId("push");
                popup.show(labelBuffer, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());

            });
        }
    }


    void deleteGoalOnAction() {

        makeViewListGoalEditor();

        deleteGoal.setOnMouseClicked(event -> {
            for (Label label : goalsListField.getItems()) {
                if (label.getId() != null && label.getId().equals("push")) {
                    for (Goal goal : goalsList) {
                        if (label.getText().equals(goal.getGoal())) {
                            Connection.getInstance().sendMessage(MessageType.DELETE_GOAL_DATA);
                            Connection.getInstance().readMessage();
                            Connection.getInstance().sendMessage(goal);
                            goalsLabelList.remove(goal);
                            break;
                        }
                    }
                    goalsListField.getItems().remove(label);
                    break;
                }
            }

        });

    }

    void updateGoalOnAction() {


        makeViewListGoalEditor();
        updateGoal.setOnMouseClicked(event -> {

            Label currentLable = null;
            for (Label label : goalsListField.getItems()) {
                if (label.getId() != null && label.getId().equals("push")) {
                    currentLable = label;
                }
            }
            Goal currentGoal = null;
            for (Goal goal : goalsList) {
                if (currentLable.getText().equals(goal.getGoal())) {
                    currentGoal = goal;
                }
            }

            currentLabelChoice = currentLable;
            currentGoalChoice = currentGoal;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_goal.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            UpdateGoalData controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();


        });


    }

    private void setStyles() {
        String fonColor = "#d7e5d2";
        String buttonColor = "#a8d06f";
        buttonPane.setStyle("-fx-background-color: " + buttonColor);
        buttonPaneNext.setStyle("-fx-background-color: " + buttonColor);

        anchor.setStyle("-fx-background-color: " + fonColor);

        //mainAnchor.setStyle("-fx-background-color: "+ fonColor);
        //secondAnchor.setStyle("-fx-background-color: "+ fonColor);
        listView.setStyle("-fx-background-color: " + fonColor);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        DataPool dataPool = new DataPool();
        statisticsDataList = dataPool.setList();
        goalsLabelList = convertToLabelList(dataPool.set2List());
        goalsList = dataPool.set2List();

        // table
        operation.setCellValueFactory(cellData -> cellData.getValue().operationProperty());
        content.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        cash.setCellValueFactory(cellData -> cellData.getValue().cashProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().getDateToStringProperty());
        checker.setCellValueFactory(new PropertyValueFactory<>("checker"));
        checker.setSortable(false);

        table.setItems(statisticsDataList);


        Label add = new Label();
        Label update = new Label();
        Label delete = new Label();
        JFXListView<Control> find = new JFXListView<>();
        Label downloadExcel = new Label();
        Label makeReport = new Label();
        Label makeGoal = new Label();


        contentFind = new JFXTextField();
        contentFind.setPromptText("содержание");


        quantityFind = new JFXTextField();
        quantityFind.setPromptText("количество");


        cashFind = new JFXTextField();
        cashFind.setPromptText("средства");


        double prefHeiqht = 54;
        double prefWidht = 185;
        int labelTextSize = 16;

        String labelColor = "-fx-background-color: #a8d06f";

        find.getItems().addAll(contentFind, quantityFind, cashFind);
        find.setPrefHeight(140);
        find.setStyle("-fx-background-color: #d7e5d2");
        Label labelF = new Label("\t  поиск");
        labelF.setStyle(labelColor);
        labelF.setPrefSize(180, prefHeiqht);
        labelF.setFont(new Font(labelTextSize));
        find.setGroupnode(labelF);


        add.setText("\t      добавить");
        add.setStyle(labelColor);
        add.setPrefSize(prefWidht, prefHeiqht);
        add.setFont(new Font(labelTextSize));


        update.setText("\t      изменить");
        update.setStyle(labelColor);
        update.setPrefSize(prefWidht, prefHeiqht);
        update.setFont(new Font(labelTextSize));
        delete.setText("\t       удалить");
        delete.setStyle(labelColor);
        delete.setPrefSize(prefWidht, prefHeiqht);
        delete.setFont(new Font(labelTextSize));
        downloadExcel.setText("\t      загрузить");
        downloadExcel.setStyle(labelColor);
        downloadExcel.setPrefSize(prefWidht, prefHeiqht);
        downloadExcel.setFont(new Font(labelTextSize));
        makeReport.setText("\t\tотчёт");
        makeReport.setStyle(labelColor);
        makeReport.setPrefSize(prefWidht, prefHeiqht);
        makeReport.setFont(new Font(labelTextSize));
        makeGoal.setText("    сформировать цель");
        makeGoal.setStyle(labelColor);
        makeGoal.setPrefSize(prefWidht, prefHeiqht);
        makeGoal.setFont(new Font(labelTextSize));


        listView.getItems().addAll(add, update, delete, find, makeReport, makeGoal);


        goalsListField.setItems(goalsLabelList);


        deleteGoalOnAction();
        updateGoalOnAction();


        add.setOnMouseClicked((MouseEvent ME) -> {
            add();
        });
        update.setOnMouseClicked((MouseEvent ME) -> {
            update();
        });
        delete.setOnMouseClicked((MouseEvent ME) -> {
            delete();
        });
        find.getGroupnode().setOnMouseClicked((MouseEvent ME) -> {


            find();
            if (buttonPane.isVisible()) {
                buttonPane.setVisible(false);
            } else {
                buttonPane.setVisible(true);
            }
        });

        makeReport.setOnMouseClicked((MouseEvent ME) -> {
            makeReport();
        });
        makeGoal.setOnMouseClicked((MouseEvent ME) -> {
            makeGoal();
        });

        setStyles();


    }

//TODO таблица дата к прав краю, цифру по разряду
}

